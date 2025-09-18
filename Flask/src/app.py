from flask import Flask, request, jsonify
from predict_sentiment import sentiment_predict

app = Flask(__name__)

@app.route("/health", methods=["GET"])
def health():
    return jsonify({"status": "ok"})

@app.route("/predict", methods=["POST"])
def predict():
    data = request.get_json(force=True)
    text = data.get("text", "")
    if not text or not isinstance(text, str):
        return jsonify({"error": "text field required"}), 400
    try:
        res = sentiment_predict(text)
        return jsonify(res)
    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=8000, debug=False)
