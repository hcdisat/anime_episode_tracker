package com.hcdisat.animetracker.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.hcdisat.animetracker.viewmodels.AnimeViewModel

abstract class AnimeBaseFragment: Fragment() {
    protected val animeViewModel: AnimeViewModel by activityViewModels()
}