import re
import pickle
from konlpy.tag import Okt
from tensorflow.keras.preprocessing.sequence import pad_sequences
from tensorflow.keras.models import load_model
import numpy as np
import os

MODEL_PATH = "model/wego_sentiment.h5"
TOKENIZER_PATH = "model/wego_tokenizer.pickle"
max_len = 30
stopwords = ['의','가','이','은','들','는','좀','잘','걍','과','도','를','으로','자','에','와','한','하다']

# 로드
if not os.path.exists(MODEL_PATH):
    raise FileNotFoundError(f"Model not found at {MODEL_PATH}")
model = load_model(MODEL_PATH)

with open(TOKENIZER_PATH, 'rb') as f:
    tokenizer = pickle.load(f)

okt = Okt()

def sentiment_predict(text):
    """
    입력: 한 문장(문자열)
    출력: dict: {"label": "positive"/"negative", "prob": 0.76}
    """
    # 정규화: 한글과 공백만 남김
    text = re.sub('[^ㄱ-ㅎㅏ-ㅣ가-힣 ]', '', text)

    # 형태소 분석 및 불용어 제거
    tokens = okt.morphs(text, stem=True)
    tokens = [t for t in tokens if t not in stopwords]

    # 정수 시퀀스 및 패딩
    seq = tokenizer.texts_to_sequences([tokens])
    pad = pad_sequences(seq, maxlen=max_len)

    # 예측
    prob = float(model.predict(pad)[0][0])
    label = "positive" if prob >= 0.5 else "negative"
    return {"label": label, "prob": prob}

# 간단한 테스트용 실행 블록
if __name__ == "__main__":
    while True:
        s = input("문장> ")
        if s.strip() == "":
            break
        print(sentiment_predict(s))
