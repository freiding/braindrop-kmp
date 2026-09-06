package by.freiding.braindrop.feature.vocabulary.data.datasource.seed

import by.freiding.braindrop.feature.vocabulary.domain.model.Chunk
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.A2
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.B1
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.B2
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkTheme.TRAVEL
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_1000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_2000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_5000

internal val TRAVEL_CHUNKS: List<Chunk> = listOf(
    chunk(
        id = "catch_a_flight",
        text = "catch a flight",
        translation = "успеть на самолёт",
        headword = "flight",
        theme = TRAVEL,
        level = A2,
        frequencyBand = TOP_1000,
        pattern = "V + N",
        example = ex(
            "We have to leave now to catch our flight.",
            "Нам нужно выезжать сейчас, чтобы успеть на самолёт.",
        ),
        more = listOf(
            ex("She barely caught her flight to Rome.", "Она едва успела на рейс в Рим."),
            ex(
                "If we miss this train, we won't catch the flight.",
                "Если опоздаем на этот поезд, не успеем на самолёт.",
            ),
        ),
        error = err("get a flight", "«Успеть на рейс» — catch a flight; get a flight значит «купить билет / улететь»."),
        collocations = listOf(
            col("miss a flight", "опоздать на рейс"),
            col("book a flight", "забронировать рейс"),
            col("a connecting flight", "стыковочный рейс"),
        ),
        nearby = listOf("catch a train", "make the connection"),
    ),
    chunk(
        id = "check_into_a_hotel",
        text = "check into a hotel",
        translation = "заселиться в отель",
        headword = "hotel",
        theme = TRAVEL,
        level = A2,
        frequencyBand = TOP_1000,
        pattern = "V + prep + N",
        example = ex("We checked into the hotel around noon.", "Мы заселились в отель около полудня."),
        more = listOf(
            ex("You can check into the hotel after 2 p.m.", "Заселиться в отель можно после 14:00."),
            ex("They checked in late and left early.", "Они заселились поздно и уехали рано."),
        ),
        collocations = listOf(
            col("check out of a hotel", "выехать из отеля"),
            col("book a hotel", "забронировать отель"),
        ),
        nearby = listOf("drop off your bags", "settle in"),
    ),
    chunk(
        id = "go_sightseeing",
        text = "go sightseeing",
        translation = "осматривать достопримечательности",
        headword = "sightseeing",
        theme = TRAVEL,
        level = A2,
        frequencyBand = TOP_5000,
        pattern = "V + N",
        example = ex("We spent the whole day going sightseeing.", "Мы весь день осматривали достопримечательности."),
        more = listOf(
            ex("Let's go sightseeing before it gets too hot.", "Давай осмотрим город, пока не стало слишком жарко."),
            ex("There wasn't much time to go sightseeing.", "Времени на осмотр достопримечательностей почти не было."),
        ),
        error = err("do sightseeing", "Обычно go sightseeing; «do sightseeing» звучит неестественно."),
        collocations = listOf(
            col("a sightseeing tour", "экскурсия по городу"),
            col("see the sights", "осмотреть достопримечательности"),
        ),
    ),
    chunk(
        id = "get_around_the_city",
        text = "get around the city",
        translation = "передвигаться по городу",
        headword = "around",
        theme = TRAVEL,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + adv + N",
        example = ex("It's easy to get around the city by metro.", "По городу удобно передвигаться на метро."),
        more = listOf(
            ex("How do you get around without a car here?", "Как здесь передвигаться без машины?"),
            ex("A bike is the fastest way to get around.", "Велосипед — самый быстрый способ передвигаться."),
        ),
        collocations = listOf(
            col("get around by bus", "ездить на автобусе"),
            col("public transport", "общественный транспорт"),
        ),
    ),
    chunk(
        id = "pack_a_suitcase",
        text = "pack a suitcase",
        translation = "собирать чемодан",
        headword = "suitcase",
        theme = TRAVEL,
        level = A2,
        frequencyBand = TOP_2000,
        pattern = "V + N",
        example = ex("I still need to pack my suitcase for the trip.", "Мне ещё нужно собрать чемодан в дорогу."),
        more = listOf(
            ex("She packed her suitcase in ten minutes.", "Она собрала чемодан за десять минут."),
            ex("Don't pack your suitcase too full.", "Не набивай чемодан слишком плотно."),
        ),
        collocations = listOf(
            col("unpack a suitcase", "разобрать чемодан"),
            col("a carry-on suitcase", "чемодан ручной клади"),
        ),
        nearby = listOf("get packed", "travel light"),
    ),
    chunk(
        id = "book_a_room",
        text = "book a room",
        translation = "забронировать номер",
        headword = "room",
        theme = TRAVEL,
        level = A2,
        frequencyBand = TOP_1000,
        pattern = "V + N",
        example = ex("I booked a room with a sea view.", "Я забронировал номер с видом на море."),
        more = listOf(
            ex("You should book a room well in advance in summer.", "Летом номер стоит бронировать сильно заранее."),
            ex("They booked a double room for two nights.", "Они забронировали двухместный номер на две ночи."),
        ),
        error = err("reserve a room", "«Reserve» тоже понятно, но в разговорной речи чаще book a room."),
        collocations = listOf(
            col("a single room", "одноместный номер"),
            col("cancel a booking", "отменить бронь"),
        ),
        forms = listOf(verb("book"), noun("booking")),
    ),
    chunk(
        id = "miss_the_bus",
        text = "miss the bus",
        translation = "опоздать на автобус",
        headword = "bus",
        theme = TRAVEL,
        level = A2,
        frequencyBand = TOP_1000,
        pattern = "V + N",
        example = ex("Hurry up or we'll miss the bus.", "Поторопись, иначе мы опоздаем на автобус."),
        more = listOf(
            ex("I missed the last bus and had to walk home.", "Я опоздал на последний автобус и шёл домой пешком."),
            ex(
                "If you miss this bus, the next one is in an hour.",
                "Если опоздаешь на этот автобус, следующий через час.",
            ),
        ),
        error = err("lose the bus", "«Опоздать на автобус» — miss the bus, не «lose»."),
        collocations = listOf(
            col("catch the bus", "успеть на автобус"),
            col("get on the bus", "сесть в автобус"),
        ),
    ),
    chunk(
        id = "go_through_customs",
        text = "go through customs",
        translation = "проходить таможню",
        headword = "customs",
        theme = TRAVEL,
        level = B1,
        frequencyBand = TOP_5000,
        pattern = "V + prep + N",
        example = ex("It took an hour to go through customs.", "Прохождение таможни заняло час."),
        more = listOf(
            ex("We went through customs without any problems.", "Мы прошли таможню без проблем."),
            ex(
                "Have your passport ready before you go through customs.",
                "Приготовь паспорт, прежде чем проходить таможню.",
            ),
        ),
        collocations = listOf(
            col("clear customs", "пройти таможенный контроль"),
            col("go through security", "пройти досмотр"),
        ),
    ),
    chunk(
        id = "take_a_taxi",
        text = "take a taxi",
        translation = "взять такси",
        headword = "taxi",
        theme = TRAVEL,
        level = A2,
        frequencyBand = TOP_1000,
        pattern = "V + N",
        example = ex("Let's take a taxi to the station.", "Давай возьмём такси до вокзала."),
        more = listOf(
            ex("We took a taxi because it was raining.", "Мы взяли такси, потому что шёл дождь."),
            ex("It's cheaper to take a taxi if there are four of you.", "Если вас четверо, такси выходит дешевле."),
        ),
        error = err("go by a taxi", "Либо take a taxi, либо go by taxi (без артикля)."),
        collocations = listOf(
            col("call a taxi", "вызвать такси"),
            col("hail a taxi", "поймать такси"),
        ),
    ),
    chunk(
        id = "extend_a_visa",
        text = "extend a visa",
        translation = "продлить визу",
        headword = "visa",
        theme = TRAVEL,
        level = B2,
        frequencyBand = TOP_5000,
        pattern = "V + N",
        example = ex("She had to extend her visa for another month.", "Ей пришлось продлить визу ещё на месяц."),
        more = listOf(
            ex("You can extend the visa at the local office.", "Визу можно продлить в местном отделении."),
            ex("Extending a visa can take several weeks.", "Продление визы может занять несколько недель."),
        ),
        collocations = listOf(
            col("apply for a visa", "подать на визу"),
            col("a tourist visa", "туристическая виза"),
            col("your visa runs out", "виза заканчивается"),
        ),
    ),
    chunk(
        id = "get_lost",
        text = "get lost",
        translation = "заблудиться",
        headword = "lost",
        theme = TRAVEL,
        level = A2,
        frequencyBand = TOP_1000,
        pattern = "V + adj",
        example = ex("We got lost on the way to the old town.", "Мы заблудились по дороге в старый город."),
        more = listOf(
            ex("It's easy to get lost in these narrow streets.", "В этих узких улочках легко заблудиться."),
            ex("Don't worry if you get lost — just call me.", "Не переживай, если заблудишься, — просто позвони мне."),
        ),
        collocations = listOf(
            col("lose your way", "сбиться с пути"),
            col("find your way back", "найти дорогу назад"),
        ),
    ),
    chunk(
        id = "hire_a_car",
        text = "hire a car",
        translation = "арендовать машину",
        headword = "car",
        theme = TRAVEL,
        level = B1,
        frequencyBand = TOP_1000,
        pattern = "V + N",
        example = ex("We hired a car for the week in Spain.", "В Испании мы арендовали машину на неделю."),
        more = listOf(
            ex(
                "It's worth hiring a car if you want to see the coast.",
                "Стоит арендовать машину, если хочешь посмотреть побережье.",
            ),
            ex("They hired a car at the airport.", "Они арендовали машину в аэропорту."),
        ),
        error = err(
            "rent a car for a person",
            "В британском английском чаще hire a car; «rent» — американский вариант.",
        ),
        collocations = listOf(
            col("a hire car", "арендованная машина"),
            col("drop the car off", "сдать машину"),
        ),
    ),
    chunk(
        id = "have_a_stopover",
        text = "have a stopover",
        translation = "делать пересадку с остановкой",
        headword = "stopover",
        theme = TRAVEL,
        level = B2,
        frequencyBand = TOP_5000,
        pattern = "V + N",
        example = ex("We have a stopover in Dubai for six hours.", "У нас пересадка в Дубае на шесть часов."),
        more = listOf(
            ex(
                "They had a stopover in Iceland on the way to New York.",
                "По дороге в Нью-Йорк у них была остановка в Исландии.",
            ),
            ex(
                "A long stopover is a good chance to see the city.",
                "Долгая пересадка — хороший шанс посмотреть город.",
            ),
        ),
        collocations = listOf(
            col("a layover", "пересадка (амер.)"),
            col("a direct flight", "прямой рейс"),
        ),
    ),
)
