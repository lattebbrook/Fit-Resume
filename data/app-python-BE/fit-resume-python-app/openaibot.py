import openai
import os

openai.api_key = os.environ.get("OPENAI_API_KEY")

def fix_text_with_openai(text):
    response = openai.ChatCompletion.create(
      model="gpt-3.5-turbo-16k",
      messages=[{"role": "user", "content": f"Put the following Thai Text into the json format. And please ignore the '\ n' and put in correct format following this: 1. ชื่อ 2. อายุ 3. วันเกิด 4. เบอร์โทร 5. ที่อยู่ 6. วุฒิการศึกษา 7. ตำแหน่งปัจจุบัน 8. ที่ทำงานปัจจุบัน 9. ระยะเวลาทำงาน 10. ลักษณะงาน 11. เงินเดือนที่คาดหวัง 12. เงินเดือนปัจจุบัน: \"{text}\""}],
      max_tokens=2000
    )
    return response.choices[0].message['content'].strip()

