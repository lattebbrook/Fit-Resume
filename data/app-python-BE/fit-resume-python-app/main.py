import sys
import codecs
import PyPDF2
import openaibot as ai
import replace as repl
import json
import logging as log
import json
import glob
import os
import datetime
from pythainlp.util import normalize

# --- Documentation
# 1. Once received request from Spring Boot the app will read config.json
# 2. Then set path from and path to for python service to work
# 3. After that it will read file from path from, each file and perform PyPDF2 convert
# 4. Each convert result will be sent to AI formatter which will store data in datastructure as [key, value] where key = file name that it reads and value = coverted text
# 5. Once iteration is completed, it will save the map into json file for spring boot to use as reference further

#Initialize
sys.stdout = codecs.getwriter('utf8')(sys.stdout.buffer)
pdf_file = []
map_file_input = {}
path_correct = False

current_dir = os.getcwd()
cfg_folder = os.path.abspath(os.path.join(current_dir, '..\\..\\', 'conf'))

print("===> Incoming request: Python")

f = open(cfg_folder + '\\' + 'envconfig.json')
data = json.load(f)
path_from = data['pathFrom']
path_to = data['pathTo']
print(f"Check file from path '{path_from}' setting as path_from")
path_correct = True


path = path_from
file_content_map = {}

# Check if the folder exists
if not os.path.exists(path):
    print(f"The folder '{path}' does not exist!!")
    exist

if len(os.listdir(path)) > 0:
    print("[OK] Config path successfully configurated.")
    for file_pdf in glob.glob(os.path.join(path, "*.pdf")):

        with open(file_pdf, 'rb') as file:
            reader = PyPDF2.PdfReader(file)
            total_pages = len(reader.pages)
            pdf_text = ""

            for page in reader.pages:
                pdf_text += page.extract_text()
                normalized_text = pdf_text
                # dictionary with file name and content to the list
                file_content_map[os.path.basename(file_pdf)] = repl.remove_space(normalized_text)
else:
    print(f"The path'{path}' is empty, will not perform job.")
    print("File(s) failed to created!!! <=====")

#create json file from data
new_dict_json_map = {}

if file_content_map is not None:
    date = datetime.datetime.now()
    file_name = date.strftime("file_%Y%m%d_%H%M%S_BATCH")

    print("[OK] Formatting json file. Please be patient! It can take around 60-70s per file, since Thai language consumes more token than English.")
    print("[OK] Please stay connected to the internet at all time. Working on the AI Formatting.")

    for k, v in file_content_map.items():
        new_dict_json_map[k] = ai.fix_text_with_openai(v)
    
    json_object = json.dumps(new_dict_json_map, indent=4, ensure_ascii=False)

    with open(path_to + "\\" + file_name + ".json", "x", encoding="utf-8") as output:
        output.write(json_object)

    print("[OK] Total json file(s) created: " + str(len(new_dict_json_map)))
    print("File(s) created successfully! <=====")