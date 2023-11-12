import openai
import os

openai.api_key = os.environ.get("OPENAI_API_KEY")

def fix_text_with_openai(text):
  response = openai.ChatCompletion.create(
    model="gpt-4-1106-preview",
    messages=[{"role": "user", "content": f"โปรดช่วย reformat string นี้โดยการเอา /n ออกและจัดเรียงเป็นแนวนอน ตามตัวอย่างนี้ \n ชื่อ: (ใส่ชื่อ นามสกุล), \n อายุ: (อายุ) เป็นต้น \n \"{text}\", \n please do not comment or provide any insight."}],
    max_tokens=4096
  )
  return response.choices[0].message['content'].strip()
