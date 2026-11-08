"""Run against a fresh demo instance. Uses fictional demo accounts only."""
import json,urllib.request,urllib.error,datetime,pathlib
base='http://localhost:8080'; out=[]
def request(method,path,body=None,expect=200,record=True):
 data=None if body is None else json.dumps(body).encode()
 req=urllib.request.Request(base+path,data=data,method=method,headers={'Content-Type':'application/json'})
 try:
  with urllib.request.urlopen(req) as r: status=r.status; payload=json.load(r)
 except urllib.error.HTTPError as e: status=e.code;payload=json.load(e)
 if record:out.append({'method':method,'path':path if len(path)<150 else path[:path.rfind('/')+1]+'<TOKEN>','status':status,'response':payload})
 assert status==expect,(method,path,status,payload)
 return payload
p='ClinicDemo2026!'
admin=request('POST','/admin',{'username':'admin','password':p},record=False)['token']
doctor=request('POST','/doctor/login',{'email':'doctor1@example.com','password':p},record=False)['token']
patient=request('POST','/patient/login',{'email':'patient1@example.com','password':p},record=False)['token']
request('POST','/patient/login',{'email':'patient1@example.com','password':'wrong'},expect=401)
request('GET','/doctor')
request('GET','/doctor/filter/Alice/am/Cardiology')
request('GET','/patient/1/patient/'+patient)
request('GET','/patient/2/patient/'+patient,expect=403)
request('GET','/patient/invalid',expect=401)
tomorrow=(datetime.date.today()+datetime.timedelta(days=1)).isoformat()
av=request('GET','/doctor/availability/patient/1/'+tomorrow+'/'+patient)
assert '09:00-10:00' not in av['message']
assert '14:00-15:00' in av['message']
request('POST','/appointments/'+patient,{'doctor':{'id':1},'patient':{'id':2},'appointmentTime':tomorrow+'T14:00:00','status':0},expect=403)
request('POST','/appointments/'+patient,{'doctor':{'id':1},'patient':{'id':1},'appointmentTime':tomorrow+'T14:00:00','status':0},expect=201)
request('POST','/appointments/'+patient,{'doctor':{'id':1},'patient':{'id':1},'appointmentTime':tomorrow+'T14:00:00','status':0},expect=400)
request('GET','/appointments/'+tomorrow+'/null/'+doctor)
request('POST','/prescription/'+doctor,{'patientName':'Demo Patient 1','appointmentId':1,'medication':'Demo medication','dosage':'Demonstration only','doctorNotes':'Fictional data'},expect=201)
# Replace every live token in evidence, including routes with tokens in the middle.
text=json.dumps(out,indent=2)
for token in (admin,doctor,patient):text=text.replace(token,'<TOKEN>')
folder=pathlib.Path(__file__).resolve().parents[1]/'evidence';folder.mkdir(exist_ok=True)
(folder/'api-smoke-test.json').write_text(text)
print('PASS:',len(out),'API checks; tokens redacted from saved evidence')
