package org.example.mjworkspace.myapplication.fragments.v

import android.os.Bundle
import android.view.*
import androidx.lifecycle.observe
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.example.mjworkspace.myapplication.MainActivity
import org.example.mjworkspace.myapplication.R
import org.example.mjworkspace.myapplication.databinding.FragmentNowBinding
import org.example.mjworkspace.myapplication.di.Injectable
import org.example.mjworkspace.myapplication.di.injectViewModel
import org.example.mjworkspace.myapplication.fragments.vm.NowViewModel
import org.example.mjworkspace.myapplication.ui_deco.GridSpacingItemDecoration
import org.example.mjworkspace.myapplication.ui_deco.VerticalItemDecoration
import org.example.mjworkspace.myapplication.ui_deco.hide
import org.example.mjworkspace.myapplication.util.ConnectivityUtil
import javax.inject.Inject

class NowFragment: Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: NowViewModel
    private lateinit var binding: FragmentNowBinding
    private val adapter: NowAdapter by lazy { NowAdapter() }
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var gridLayoutManager: GridLayoutManager
    private val linearDecoration: RecyclerView.ItemDecoration by lazy {
        VerticalItemDecoration(
            resources.getDimension(R.dimen.margin_normal).toInt())
    }
    private val gridDecoration: RecyclerView.ItemDecoration by lazy {
        GridSpacingItemDecoration(
            SPAN_COUNT, resources.getDimension(R.dimen.margin_grid).toInt())
    }
    private var isLinearLayoutManager: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = injectViewModel(viewModelFactory)
        viewModel.connectivityAvailable = ConnectivityUtil.isConnected(requireContext())
        binding = FragmentNowBinding.inflate(inflater, container, false)
        context ?: return binding.root
        linearLayoutManager = LinearLayoutManager(activity)
        gridLayoutManager = GridLayoutManager(activity, SPAN_COUNT)
        setLayoutManager()
        binding.recyclerView.adapter = adapter

        subscribeUi(adapter)

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun subscribeUi(adapter: NowAdapter) {
        viewModel.nowList.observe(viewLifecycleOwner) {
            binding.progressBar.hide()
            adapter.submitList(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        setDataRepresentationIcon(menu.findItem(R.id.action_list))
        (context as MainActivity).binding.enterSearchKeyword
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_list -> {
                isLinearLayoutManager = !isLinearLayoutManager
                setDataRepresentationIcon(item)
                setLayoutManager()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setLayoutManager() {
        val recyclerView = binding.recyclerView
        var scrollPosition = 0
        // If a layout manager has already been set, get current scroll position.
        if (recyclerView.layoutManager != null) {
            scrollPosition = (recyclerView.layoutManager as LinearLayoutManager)
                .findFirstCompletelyVisibleItemPosition()
        }

        if (isLinearLayoutManager) {
            recyclerView.removeItemDecoration(gridDecoration)
            recyclerView.addItemDecoration(linearDecoration)
            recyclerView.layoutManager = linearLayoutManager
        } else {
            recyclerView.removeItemDecoration(linearDecoration)
            recyclerView.addItemDecoration(gridDecoration)
            recyclerView.layoutManager = gridLayoutManager
        }

        recyclerView.scrollToPosition(scrollPosition)
    }

    private fun setDataRepresentationIcon(item: MenuItem) {
        item.setIcon(if (isLinearLayoutManager)
            R.drawable.ic_grid_black_24dp
        else R.drawable.ic_list_black_24dp
        )
    }

    companion object {
        const val SPAN_COUNT = 3
    }

}