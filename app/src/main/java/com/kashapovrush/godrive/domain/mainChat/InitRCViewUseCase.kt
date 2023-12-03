package com.kashapovrush.godrive.domain.mainChat

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.Query
import javax.inject.Inject

class InitRCViewUseCase @Inject constructor(private val repository: MainRepository) {

    operator fun invoke(rv: RecyclerView, llm: LinearLayoutManager) {
        repository.initRCView(rv, llm)
    }
}