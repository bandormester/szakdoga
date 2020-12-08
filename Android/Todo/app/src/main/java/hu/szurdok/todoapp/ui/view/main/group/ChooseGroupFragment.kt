package hu.szurdok.todoapp.ui.view.main.group

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import hu.szurdok.todoapp.R
import hu.szurdok.todoapp.data.adapters.ChooseGroupAdapter
import hu.szurdok.todoapp.data.models.Group
import hu.szurdok.todoapp.ui.view.main.MainActivity
import hu.szurdok.todoapp.viewmodel.main.group.ChooseGroupViewModel
import kotlinx.android.synthetic.main.fragment_choose_group.*


class ChooseGroupFragment : Fragment(), ChooseGroupAdapter.GroupItemClickListener {

    private lateinit var chooseGroupViewModel: ChooseGroupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_choose_group, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chooseGroupViewModel =
            (activity as MainActivity).mainContainer.chooseGroupViewModelFactory.create((activity as MainActivity).token)

        btCreateGroup.setOnClickListener {
            val dialog = CreateGroupFragment(chooseGroupViewModel)
            dialog.show(
                (activity as MainActivity).supportFragmentManager,
                CreateGroupFragment.TAG
            )
        }

        btJoinGroup.setOnClickListener {
            joinDialog()
            }

        chooseGroupViewModel.groups.observe(viewLifecycleOwner) {
            setupRecyclerView(it)
        }
    }

    private fun setupRecyclerView(list: List<Group>) {
        rvGroups.layoutManager = LinearLayoutManager(activity)
        val groupAdapter = ChooseGroupAdapter(chooseGroupViewModel, requireContext())
        groupAdapter.itemClickListener = this
        groupAdapter.addAll(list)
        rvGroups.adapter = groupAdapter
    }

    private fun joinDialog(){
        val builder = AlertDialog.Builder(requireContext())
        val editText : EditText = EditText(requireContext())
        builder.setMessage("Enter your join code here")
        builder.setTitle("Join dialog")
        builder.setView(editText)
        builder.setPositiveButton("Join"
        ) { _, _ ->
            val text = editText.text.toString()
            chooseGroupViewModel.joinGroup(text)
        }
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.show()
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, null))

    }

    override fun onGroupSelected(group: Group) {
        (activity as MainActivity).groupSelected(group.id)
    }
}