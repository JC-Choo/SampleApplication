package dev.chu.twowaybinding

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _userEmailLD by lazy { MutableLiveData("test@gmail.com") }
    val userEmailLD: MutableLiveData<String> by lazy { _userEmailLD }

    val count = 1

    val name = "Andro"

    fun onEmailChange(){
        if(_userEmailLD.value.equals("test@gmail.com")){
            _userEmailLD.value = "android@gmail.com"
        }else{
            _userEmailLD.value = "test@gmail.com"
        }
    }

    fun onUpdateReceived(number:Int){
        if(number%2 == 0){
            _userEmailLD.value = "android@gmail.com"
        }else{
            _userEmailLD.value = "test@gmail.com"
        }
    }
}