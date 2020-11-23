package hu.szurdok.todoapp.ui.view.login

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import hu.szurdok.todoapp.R
import hu.szurdok.todoapp.viewmodel.login.RegisterViewModel
import kotlinx.android.synthetic.main.register_activity.*

class RegisterFragment : Fragment() {

    private lateinit var registerViewModel : RegisterViewModel
    private var hasPicture = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.register_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerViewModel = (activity as LoginActivity).loginContainer.registerViewModelFactory.create()

        registerViewModel.registrationStatus.observe(viewLifecycleOwner){
            if(it.message.isNotEmpty()) Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
            if(it.successful) (activity as LoginActivity).backToLogin()
        }

        btBack.setOnClickListener {
            (activity as LoginActivity).backToLogin()
        }

        btCreate.setOnClickListener {
            if(validateInput()){
                registerViewModel.register(
                    ivCreateGroup.drawToBitmap(),
                    etRegFullname.text.toString(),
                    etRegUsername.text.toString(),
                    etRegEmail.text.toString(),
                    etRegPassword.text.toString(),
                    hasPicture)
            }
            else{
                Toast.makeText(activity, "Fill all boxes !", Toast.LENGTH_LONG).show()
            }
        }

        ivCreateGroup.setOnClickListener{
            if(hasPicture){
                ivCreateGroup.setImageResource(R.drawable.ic_launcher_background)
            }
            else {
                Log.d("ivReg", "Pressed")
                askCameraPermission()
            }
            hasPicture = !hasPicture
        }
    }

    private fun askCameraPermission() {
        if(ContextCompat.checkSelfPermission(requireActivity().baseContext,
                android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf<String>(android.Manifest.permission.CAMERA),
                101)
        }
        else{
            openCamera()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 101 && resultCode == RESULT_OK){
            val bitmap = data!!.extras!!.get("data") as Bitmap
            ivCreateGroup.setImageBitmap(bitmap)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == 101){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openCamera()
            }
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 0)
    }

    private fun validateInput() : Boolean{
        return etRegFullname.text.isNotEmpty()
                && etRegUsername.text.isNotEmpty()
                && etRegEmail.text.isNotEmpty()
                && etRegPassword.text.isNotEmpty()
                //&& etRegRepeatPassword.text == etRegPassword.text
    }
}