import re
import pickle
import numpy as np
import pandas as pd
from tqdm import tqdm
from konlpy.tag import Okt
from tensorflow.keras.preprocessing.text import Tokenizer
from tensorflow.keras.preprocessing.sequence import pad_sequences
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Embedding, LSTM, Dense
from tensorflow.keras.callbacks import EarlyStopping, ModelCheckpoint

# 설정
TRAIN_PATH = "data/ratings_train.txt"   # 실제 경로로 변경
TEST_PATH = "data/ratings_test.txt"     # 실제 경로로 변경
MODEL_OUT = "model/wego_sentiment.h5"
TOKENIZER_OUT = "model/wego_tokenizer.pickle"
max_len = 30
threshold = 2   # 등장 빈도 임계치(예시)
okt = Okt()
stopwords = ['의','가','이','은','들','는','좀','잘','걍','과','도','를','으로','자','에','와','한','하다']

# 1) 데이터 로드
train_df = pd.read_table(TRAIN_PATH)
test_df = pd.read_table(TEST_PATH)

# 2) 기본 정제: null/중복 제거, 한글만 남기기
train_df = train_df.dropna(how='any').drop_duplicates(subset=['document'])
test_df = test_df.dropna(how='any').drop_duplicates(subset=['document'])

# 정규식으로 한글과 공백만 남김
train_df['document'] = train_df['document'].str.replace('[^ㄱ-ㅎㅏ-ㅣ가-힣 ]','', regex=True)
test_df['document'] = test_df['document'].str.replace('[^ㄱ-ㅎㅏ-ㅣ가-힣 ]','', regex=True)

# 공백만 남는 행(빈문장) 제거
train_df['document'] = train_df['document'].str.replace('^ +', '', regex=True)
test_df['document'] = test_df['document'].str.replace('^ +', '', regex=True)
train_df = train_df[train_df['document'].str.len() > 0].reset_index(drop=True)
test_df = test_df[test_df['document'].str.len() > 0].reset_index(drop=True)

# 3) 형태소 분석 및 불용어 제거 → 토큰 리스트 생성
X_train = []
for sent in tqdm(train_df['document'], desc="tokenizing train"):
    tokens = okt.morphs(sent, stem=True)
    tokens = [w for w in tokens if not w in stopwords]
    X_train.append(tokens)

X_test = []
for sent in tqdm(test_df['document'], desc="tokenizing test"):
    tokens = okt.morphs(sent, stem=True)
    tokens = [w for w in tokens if not w in stopwords]
    X_test.append(tokens)

# 4) 토큰 빈도로 희귀 단어 제거하여 vocab_size 결정
tokenizer_temp = Tokenizer()
tokenizer_temp.fit_on_texts(X_train)

total_cnt = len(tokenizer_temp.word_index)        # 전체 단어 수
rare_cnt = 0
total_freq = 0
rare_freq = 0
for key, value in tokenizer_temp.word_counts.items():
    total_freq += value
    if value < threshold:
        rare_cnt += 1
        rare_freq += value

vocab_size = total_cnt - rare_cnt + 1   # +1 for padding token
print("vocab_size:", vocab_size)

# 5) 실제 학습용 tokenizer 생성 후 시퀀스화
tokenizer = Tokenizer(vocab_size, oov_token=None)
tokenizer.fit_on_texts(X_train)
X_train_seq = tokenizer.texts_to_sequences(X_train)
X_test_seq = tokenizer.texts_to_sequences(X_test)

# 6) 토크나이저 저장
import os
os.makedirs(os.path.dirname(TOKENIZER_OUT), exist_ok=True)
with open(TOKENIZER_OUT, 'wb') as f:
    pickle.dump(tokenizer, f)

# 7) 라벨 준비 및 빈 시퀀스 제거
y_train = np.array(train_df['label'])
y_test = np.array(test_df['label'])

drop_train = [i for i, s in enumerate(X_train_seq) if len(s) < 1]
X_train_seq = np.delete(X_train_seq, drop_train, axis=0)
y_train = np.delete(y_train, drop_train, axis=0)

# (테스트도 동일 처리)
drop_test = [i for i, s in enumerate(X_test_seq) if len(s) < 1]
X_test_seq = np.delete(X_test_seq, drop_test, axis=0)
y_test = np.delete(y_test, drop_test, axis=0)

# 8) 패딩(토큰 개수 통일)
X_train_pad = pad_sequences(X_train_seq, maxlen = max_len)
X_test_pad = pad_sequences(X_test_seq, maxlen = max_len)

# 9) 모델 정의 및 학습
embedding_dim = 100
hidden_units = 128

model = Sequential()
model.add(Embedding(vocab_size, embedding_dim, input_length=max_len))
model.add(LSTM(hidden_units))
model.add(Dense(1, activation='sigmoid'))

es = EarlyStopping(monitor='val_loss', mode='min', verbose=1, patience=4)
mc = ModelCheckpoint(MODEL_OUT, monitor='val_acc', mode='max', verbose=1, save_best_only=True)

model.compile(optimizer='rmsprop', loss='binary_crossentropy', metrics=['acc'])
history = model.fit(X_train_pad, y_train, epochs=15, batch_size=64, validation_split=0.2, callbacks=[es, mc])

# 10) 최종 테스트 정확도 출력
from tensorflow.keras.models import load_model
loaded = load_model(MODEL_OUT)
acc = loaded.evaluate(X_test_pad, y_test)[1]
print("테스트 정확도: %.4f" % acc)
