# coding: interpy

import os,time
from espeak import espeak as r
from pocketsphinx import LiveSpeech, get_model_path

model_path = get_model_path()

speech = LiveSpeech(
    verbose=True,
    sampling_rate=16000,
    buffer_size=4096,
    #buffer_size=512,
    no_search=False,
    full_utt=False,
    hmm=os.path.join(model_path, 'en-us'),
    lm=os.path.join(model_path, 'en-us.lm.bin'),
    kws=os.path.join(model_path, 'keyphrase.list'),
    dic=os.path.join(model_path, 'cmudict-en-us.dict')
)

for phrase in speech:
    kw = str(phrase.hypothesis()).replace('and','')
    if(kw == "lights on" or kw == "lights lawn" or kw == "white sauce"): 
        print("Turning lights on")
        r.synth("Turning lights on.")
        time.sleep(1)
        #os.system('curl --data "state=1&ip=192.168.1.4" 192.168.1.9:3000')
        os.system('/home/rachel/Documents/Java/HomeAutomation/C/client2 192.168.1.177 9486 onn')
        #break
    if(kw == "lights off" or kw == "lights all"): 
        print("Turning lights off")
        r.synth("Turning lights off.")
        time.sleep(1)
        #os.system('curl --data "state=0&ip=192.168.1.4" 192.168.1.9:3000')
        os.system('/home/rachel/Documents/Java/HomeAutomation/C/client2 192.168.1.177 9486 off')
        #break
    else:
        print("You said: #{kw}")

