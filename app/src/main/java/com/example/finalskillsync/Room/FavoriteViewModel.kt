package com.example.finalskillsync.Room

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class FavoriteViewModel(private val repository: OppRepository) : ViewModel() {

    val allOpps: LiveData<List<Opp>> = repository.readAllData
}