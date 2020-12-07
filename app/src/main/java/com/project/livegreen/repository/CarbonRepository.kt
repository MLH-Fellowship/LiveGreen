package com.project.livegreen.repository

import com.project.livegreen.service.CarbonApi

class CarbonRepository {
    suspend fun getData(lon : Double, lat : Double) = CarbonApi.carbonService.getCarbonData(lon, lat)
}