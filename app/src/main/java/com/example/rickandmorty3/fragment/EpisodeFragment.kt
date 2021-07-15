package com.example.rickandmorty3.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty3.R
import com.example.rickandmorty3.RickAndMortyApplication
import com.example.rickandmorty3.di.UniversalViewModelFactory
import com.example.rickandmorty3.fragment.adapter.EpisodeAdapter
import com.example.rickandmorty3.fragment.adapter.ItemDecoration
import javax.inject.Inject

class EpisodeFragment : Fragment(R.layout.episode_fragment) {
    
    companion object {
        private const val VISIBLE_THRESHOLD = 1
    }

    var spanCount = 1 // 3 columns
    var spacing = 16 // 50px
    var includeEdge = true // внеш отступы

    private var lastVisibleItem = 0
    private var totalItemCount = 0

    lateinit var adapter: EpisodeAdapter
    lateinit var recycler: RecyclerView

//    private val viewModel: EpisodeViewModel by lazy {
//        ViewModelProvider(this).get(EpisodeViewModel::class.java)
//    }

    @Inject
    lateinit var universalFactory: UniversalViewModelFactory

    private  val viewModel: EpisodeViewModel by viewModels { universalFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        RickAndMortyApplication.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        setupRecycler()
        configureObserved()
    }

    private fun initViews(root: View) {
        recycler = root.findViewById(R.id.rc_episode)
    }

    private fun setupRecycler(){
        val linearLayoutManager = recycler.layoutManager as LinearLayoutManager
        val itemDecoration = ItemDecoration()
        itemDecoration.setupConfiguration(spanCount,spacing,includeEdge)
        adapter = EpisodeAdapter()
        recycler.adapter = adapter
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = linearLayoutManager.itemCount
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                if (totalItemCount <= lastVisibleItem + VISIBLE_THRESHOLD) {
                    viewModel.onNewPageRequested()
                }
            }
        })
        recycler.addItemDecoration(itemDecoration)
    }

    private fun configureObserved() {
        viewModel.episode.observe(viewLifecycleOwner) {
            adapter.replaceItem(it)
        }
    }

}