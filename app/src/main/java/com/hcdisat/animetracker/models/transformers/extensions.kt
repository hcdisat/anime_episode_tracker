package com.hcdisat.animetracker.models.transformers

import com.hcdisat.animetracker.models.episodes.Attributes


fun Attributes.episodeName(): String =
    "E$number $canonicalTitle"