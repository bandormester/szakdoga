package hu.szurdok.todoapp.ui.view.main

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import hu.szurdok.todoapp.R
import hu.szurdok.todoapp.data.group.ChooseGroupAdapter
import hu.szurdok.todoapp.data.group.Group
import hu.szurdok.todoapp.viewmodel.main.ChooseGroupViewModel
import kotlinx.android.synthetic.main.choose_group_fragment.*
import kotlinx.android.synthetic.main.choose_group_fragment.view.*


class ChooseGroupFragment : Fragment(), ChooseGroupAdapter.GroupItemClickListener {

    private lateinit var chooseGroupViewModel: ChooseGroupViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.choose_group_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.background

        rvGroups.layoutManager = LinearLayoutManager(activity)

        chooseGroupViewModel =
            (activity as MainActivity).mainContainer.chooseGroupViewModelFactory.create((activity as MainActivity).token)

        chooseGroupViewModel.groups.observe(viewLifecycleOwner) {
            setupRecyclerView(it)
        }
    }

    private fun setupRecyclerView(list: List<Group>) {
        val groupAdapter = ChooseGroupAdapter()
        Log.d("retrofit", list.size.toString())
        groupAdapter.itemClickListener = this
        groupAdapter.addAll(list)
        rvGroups.adapter = groupAdapter
    }

    override fun onGroupSelected(group: Group) {
        (activity as MainActivity).groupSelected(group.id)
    }
}