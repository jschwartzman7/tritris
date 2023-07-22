from django.shortcuts import render
import pyrebase

firebaseConfig = {
  apiKey: "AIzaSyCFJWXt_vJJmeXLah6mdj5X2KbdTuq7fx8",
  authDomain: "new-ranking-390908.firebaseapp.com",
  projectId: "new-ranking-390908",
  storageBucket: "new-ranking-390908.appspot.com",
  messagingSenderId: "761406650441",
  appId: "1:761406650441:web:645384eec314887368a798"
}

#here we are doing firebase authentication
firebase=pyrebase.initialize_app(firebaseConfig)
authe = firebase.auth()
database=firebase.database()


# Create your views here.
from django.http import HttpResponse



def index(request):
    return HttpResponse("Hello, world. You're at the polls index.")
