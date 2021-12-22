import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marketplace.MyApplication
import com.example.marketplace.model.ForgotPasswordRequest
import com.example.marketplace.model.LoginRequest
import com.example.marketplace.model.User
import com.example.marketplace.repository.Repository
import kotlinx.coroutines.launch

class LoginViewModel(val context: Context, val repository: Repository) : ViewModel() {
    var token: MutableLiveData<String> = MutableLiveData()
    var user = MutableLiveData<User>()

    init {
        user.value = User()
    }

    suspend fun login() {
        val request =
            LoginRequest(username = user.value!!.username, password = user.value!!.password)
        try {
            val result = repository.login(request)
            MyApplication.token = result.token
            token.value = result.token
            Log.d("xxx", "MyApplication - token:  ${MyApplication.token}")
        } catch (e: Exception) {
            Toast.makeText(context, "Can't login to this account!\n" ,Toast.LENGTH_LONG).show()
            Log.d("xxx", "LoginViewModel - exception: ${e.toString()}")
        }
    }

    suspend fun forgotPassword(username: String, email: String) {
        val request =
            ForgotPasswordRequest(username, email)
        try {
            val result = repository.forgotPassword(request)
            Log.d("xxx", "MyApplication - token:  ${result.message}")
        } catch (e: Exception) {
            Toast.makeText(context, "Can't login to this account!\n" ,Toast.LENGTH_LONG).show()
            Log.d("xxx", "LoginViewModel - exception: ${e.toString()}")
        }
    }
}
