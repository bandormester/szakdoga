package hu.szurdok.todoapp.ui.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import hu.szurdok.todoapp.R
import hu.szurdok.todoapp.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.login_activity.*

class LoginFragment : Fragment(){
    private lateinit var loginViewModel : LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.login_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel = (activity as LoginActivity).appContainer.getLoginContainer().loginViewModel

//        etUsername.addTextChangedListener(object : TextWatcher  {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//
//            override fun afterTextChanged(s: Editable?) {
//                if (etUsername.text.isEmpty()) {
//                    etUsername.error = "Enter FirstName";
//                } else {
//                    etUsername.error = null;
//                }
//            }
//        })
//
//        etPassword.addTextChangedListener(object : TextWatcher  {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
//
//            override fun afterTextChanged(s: Editable?) {
//                if (etPassword.text.isEmpty()) {
//                    etPassword.error = "Enter Password";
//                } else {
//                    etPassword.error = null;
//                }
//            }
//        })
//       etPassword.error = " asd "

        btLogin.setOnClickListener {
            if(etUsername.text.isNullOrEmpty() || etPassword.text.isNullOrEmpty()) {
                Toast.makeText(activity, "Enter credentials", Toast.LENGTH_LONG).show()
            }else{
                loginViewModel.tryLogin(etUsername.text.toString(), etPassword.text.toString())
            }
        }

        btRegister.setOnClickListener{
            (activity as LoginActivity).toRegisterFragment()
        }
    }
}