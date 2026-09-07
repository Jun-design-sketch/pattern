import os
from dotenv import load_dotenv
from datetime import datetime
from pathlib import Path
import json
from mlx_lm import generate, load
from mlx_lm.sample_utils import make_sampler
# ----------------------------------------------
load_dotenv()
HF_TOKEN = os.getenv("HF_TOKEN")
if not HF_TOKEN:
  raise RuntimeError(
    "HF_TOKENが見つかりません"
  )
MODEL_NAME = "mlx-community/Qwen3-4B-4bit"
# ----------------------------------------------
print("モデル読み込み中...")
model, tokenizer = load(MODEL_NAME)
# ----------------------------------------------
messages = [
    {
        "role": "system",
        "content": "あなたは親切かつ簡潔に答えるAIアシスタントです。",
    }
]
print("\nローカルLLM開始。終わらせるにはexitを入力してください。\n")
# ----------------------------------------------
while True:
    user_input = input("私: ").strip()

    if user_input.lower() == "exit":
        break

    if not user_input:
        continue

    messages.append({"role": "user", "content": user_input})

    prompt = tokenizer.apply_chat_template(
        messages,
        tokenize=False,
        add_generation_prompt=True,
        enable_thinking=False,
    )

    print("AI: ", end="", flush=True)
    response = generate(
        model,
        tokenizer,
        prompt=prompt,
        max_tokens=300,
        sampler=make_sampler(temp=0.7),
        verbose=False,
    )
    print(response)

    messages.append({"role": "assistant", "content": response})
# ----------------------------------------------
PROJECT_DIR = Path(__file__).resolve().parent
CHAT_DIR = PROJECT_DIR / "chats"
CHAT_DIR.mkdir(exist_ok=True)
filename = CHAT_DIR / datetime.now().strftime(
    "chat_%Y%m%d_%H%M%S.json"
)

with open(filename, "w", encoding="utf-8") as f:
    json.dump(messages, f, ensure_ascii=False, indent=2)

print(f"\n会話保存完了: {filename}")