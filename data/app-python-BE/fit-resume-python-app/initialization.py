import os
import fileinput

#load configuration from config folder 
current_dir = os.getcwd()
cfg_folder = os.path.abspath(os.path.join(current_dir, '..\\..\\..\\', 'conf'))
f = cfg_folder + '\\' + 'envconfig.json'

print("===> PYTHON SCRIPT RUN SUCCESSFULLY")
print("SETTING UP PYTHON SCRIPT, SETTING UP ENVIRONMENT VARIABLE...")

with open(f, 'r') as file:
    filedata = file.read()

# Replace the target string
filedata = filedata.replace('$DB_USERNAME', os.environ.get('MONGO_DB_ENV'))
filedata = filedata.replace('$AES128_Key', os.environ.get('AES128_KEY'))

with open(f, 'w') as file:
  file.write(filedata)

print("LOADED ENVIRONMENT VARIABLE INTO JSON FILE <=== ")