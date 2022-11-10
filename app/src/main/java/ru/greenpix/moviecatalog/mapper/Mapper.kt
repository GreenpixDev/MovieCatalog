package ru.greenpix.moviecatalog.mapper

interface Mapper<Source, Dest> {

    fun map(source: Source): Dest

}