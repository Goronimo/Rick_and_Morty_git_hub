package com.example.rickandmorty3.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty3.R
import com.example.rickandmorty3.fragment.adapter.CharactersAdapter
import com.example.rickandmorty3.fragment.adapter.ItemDecoration
import javax.inject.Inject

class CharactersFragment : Fragment(R.layout.fragment_characters_list),
    CharactersAdapter.OnCardClickListener {

    companion object {
        private const val VISIBLE_THRESHOLD = 1
    }
    // recycler.itemDecoration
    var spanCount = 3 // 3 columns
    var spacing = 16 // 50px
    var includeEdge = true // внеш отступы

    private var lastVisibleItem = 0
    private var totalItemCount = 0

    lateinit var adapter: CharactersAdapter
    lateinit var recycler: RecyclerView

    private val viewModel: CharactersViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        setupRecycler()
        configureObservers()
    }

    private fun initViews(root: View) {
        recycler = root.findViewById(R.id.rv_characters)
    }

    private fun setupRecycler() {
        val gridLayoutManager = recycler.layoutManager as GridLayoutManager
        val itemDecoration = ItemDecoration()
        itemDecoration.setupConfiguration(spanCount,spacing,includeEdge)
        adapter = CharactersAdapter()
        recycler.adapter = adapter
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                // общее колл карточек
                totalItemCount = gridLayoutManager.itemCount
                //последняя видимая карточка
                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition()
                // tru и все карточки <= (последняя видимая карточка + предел на одной странице)
                if (totalItemCount <= lastVisibleItem + VISIBLE_THRESHOLD) {
                    viewModel.onNewPageRequested()
                }
            }
        })
        recycler.addItemDecoration(itemDecoration)
    }

    private fun configureObservers() {
        viewModel.characters.observe(viewLifecycleOwner) {
            adapter.replaceItem(it, this)
        }
    }

    override fun onCardClick(position: Int) {
        viewModel.onCardClicked(position)
    }
}