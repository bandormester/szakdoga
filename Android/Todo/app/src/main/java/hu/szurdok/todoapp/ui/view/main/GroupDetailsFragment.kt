package hu.szurdok.todoapp.ui.view.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import hu.szurdok.todoapp.R
import hu.szurdok.todoapp.data.user.GroupMemberAdapter
import hu.szurdok.todoapp.data.user.User
import hu.szurdok.todoapp.viewmodel.main.GroupDetailsViewModel
import kotlinx.android.synthetic.main.group_details_fragment.*

class GroupDetailsFragment : Fragment(), GroupMemberAdapter.MemberItemClickListener {

    private lateinit var groupDetailsViewModel: GroupDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.group_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lvMembers.layoutManager = LinearLayoutManager(activity)

        groupDetailsViewModel = (activity as MainActivity).mainContainer.groupDetailsViewModelFactory
            .create((activity as MainActivity).token, (activity as MainActivity).groupId, (activity as MainActivity))

        groupDetailsViewModel.group.observe(viewLifecycleOwner){
            tvGroupName.text = it.name
        }

        groupDetailsViewModel.members.observe(viewLifecycleOwner){
            setupRecyclerView(it)
        }

        groupDetailsViewModel.removeMessage.observe(viewLifecycleOwner){
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        }

        btInvite.setOnClickListener {
            groupDetailsViewModel.copyInviteCode()
            Toast.makeText(activity, "Join code copied to clipboard", Toast.LENGTH_LONG).show()
        }
    }

    private fun setupRecyclerView(members : List<User>){
        val groupAdapter = GroupMemberAdapter()
        Log.d("retrofit", members.size.toString())
        groupAdapter.addAll(members)
        groupAdapter.itemClickListener = this
        lvMembers.adapter = groupAdapter
    }

    override fun onMemberSelected(member: User) {
        Toast.makeText(activity, "Clicked !", Toast.LENGTH_LONG).show()
    }

    override fun onMemberRemoved(member: User) {
        groupDetailsViewModel.removeMember((activity as MainActivity).groupId, (activity as MainActivity).token, member.id)
    }
}