package com.example.finalskillsync.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.finalskillsync.Model.Opp
import com.example.finalskillsync.Repository.OppRepository

class FavoriteViewModel(private val repository: OppRepository) : ViewModel() {

    val allOpps: LiveData<List<Opp>> = repository.readAllData
}