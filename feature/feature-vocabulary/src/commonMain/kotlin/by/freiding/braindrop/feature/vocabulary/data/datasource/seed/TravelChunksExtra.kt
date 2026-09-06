package by.freiding.braindrop.feature.vocabulary.data.datasource.seed

import by.freiding.braindrop.feature.vocabulary.domain.model.Chunk
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.A2
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.B1
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.B2
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkRegister.INFORMAL
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkTheme.TRAVEL
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_1000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_2000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_5000

/** Second content drop for the Travel theme. */
internal val TRAVEL_CHUNKS_EXTRA: List<Chunk> = listOf(
    chunk(
        id = "check_in_online",
        text = "check in online",
        translation = "зарегистрироваться на рейс онлайн",
        headword = "check",
        theme = TRAVEL,
        level = A2,
        frequencyBand = TOP_2000,
        pattern = "V + adv + adv",
        example = ex(
            "You can check in online 24 hours before the flight.",
            "Онлайн-регистрация открывается за 24 часа до вылета.",
        ),
        more = listOf(
            ex("I checked in online and skipped the queue.", "Я зарегистрировался онлайн и не стоял в очереди."),
            ex("Have you checked in for the flight yet?", "Ты уже зарегистрировался на рейс?"),
        ),
        collocations = listOf(
            col("a boarding pass", "посадочный талон"),
            col("check in your luggage", "сдать багаж"),
        ),
    ),
    chunk(
        id = "go_through_security",
        text = "go through security",
        translation = "проходить досмотр",
        headword = "security",
        theme = TRAVEL,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + prep + N",
        example = ex(
            "Allow extra time to go through security.",
            "Заложи дополнительное время на прохождение досмотра.",
        ),
        more = listOf(
            ex("We went through security in ten minutes.", "Досмотр мы прошли за десять минут."),
            ex("Take your laptop out before you go through security.", "Достань ноутбук перед досмотром."),
        ),
        collocations = listOf(
            col("a security check", "проверка безопасности"),
            col("hand luggage", "ручная кладь"),
        ),
    ),
    chunk(
        id = "board_a_plane",
        text = "board a plane",
        translation = "садиться в самолёт",
        headword = "board",
        theme = TRAVEL,
        level = A2,
        frequencyBand = TOP_2000,
        pattern = "V + N",
        example = ex("We started boarding the plane at gate 12.", "Посадку в самолёт объявили у выхода 12."),
        more = listOf(
            ex("Passengers with young children can board first.", "Пассажиры с маленькими детьми проходят первыми."),
            ex("They were the last to board.", "Они сели в самолёт последними."),
        ),
        error = err("get on a plane through", "«Сесть в самолёт» — board a plane или get on a plane."),
        collocations = listOf(
            col("a boarding gate", "выход на посадку"),
            col("last call for boarding", "заканчивается посадка"),
        ),
        forms = listOf(verb("board"), noun("boarding")),
    ),
    chunk(
        id = "get_a_good_deal",
        text = "get a good deal on a flight",
        translation = "найти дешёвый билет",
        headword = "deal",
        theme = TRAVEL,
        level = B1,
        frequencyBand = TOP_2000,
        register = INFORMAL,
        pattern = "V + N + prep + N",
        example = ex(
            "We got a really good deal on flights to Lisbon.",
            "Мы отхватили очень выгодные билеты до Лиссабона.",
        ),
        more = listOf(
            ex("Book early to get a good deal.", "Бронируй заранее, чтобы поймать выгодную цену."),
            ex("There are some great deals in the sale.", "На распродаже есть отличные предложения."),
        ),
        collocations = listOf(
            col("a last-minute deal", "горящее предложение"),
            col("shop around", "поискать варианты"),
        ),
    ),
    chunk(
        id = "see_someone_off",
        text = "see someone off",
        translation = "провожать кого-то",
        headword = "off",
        theme = TRAVEL,
        level = B1,
        frequencyBand = TOP_5000,
        pattern = "V + N + adv",
        example = ex("We went to the airport to see her off.", "Мы поехали в аэропорт её провожать."),
        more = listOf(
            ex("My parents saw me off at the station.", "Родители провожали меня на вокзале."),
            ex("There's no need to see me off.", "Не нужно меня провожать."),
        ),
        collocations = listOf(
            col("wave goodbye", "помахать на прощание"),
            col("pick someone up", "встретить кого-то"),
        ),
    ),
    chunk(
        id = "get_over_jet_lag",
        text = "get over jet lag",
        translation = "справиться с джетлагом",
        headword = "jet lag",
        theme = TRAVEL,
        level = B1,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + prep + N",
        example = ex(
            "It took me three days to get over the jet lag.",
            "Мне понадобилось три дня, чтобы прийти в себя после джетлага.",
        ),
        more = listOf(
            ex("She never gets jet lag flying east.", "На восток она летает без джетлага."),
            ex(
                "Sunlight helps you get over jet lag faster.",
                "Солнечный свет помогает быстрее справиться с джетлагом.",
            ),
        ),
        collocations = listOf(
            col("adjust to the time difference", "привыкнуть к разнице во времени"),
            col("be wide awake at 4 a.m.", "не спать в четыре утра"),
        ),
    ),
    chunk(
        id = "travel_light",
        text = "travel light",
        translation = "путешествовать налегке",
        headword = "light",
        theme = TRAVEL,
        level = B1,
        frequencyBand = TOP_2000,
        register = INFORMAL,
        pattern = "V + adj",
        example = ex("I always try to travel light.", "Я всегда стараюсь путешествовать налегке."),
        more = listOf(
            ex("If you travel light, you avoid baggage fees.", "Если едешь налегке, не платишь за багаж."),
            ex("She travels light — just a carry-on.", "Она ездит налегке — только ручная кладь."),
        ),
        collocations = listOf(
            col("pack light", "собрать минимум вещей"),
            col("overpack", "набрать лишнего"),
        ),
    ),
    chunk(
        id = "go_off_the_beaten_track",
        text = "go off the beaten track",
        translation = "уходить от туристических маршрутов",
        headword = "track",
        theme = TRAVEL,
        level = B2,
        frequencyBand = TOP_5000,
        pattern = "V + prep + N + idiom",
        example = ex(
            "We like to go off the beaten track when we travel.",
            "Путешествуя, мы любим сходить с туристических троп.",
        ),
        more = listOf(
            ex("This village is well off the beaten track.", "Эта деревня в стороне от туристических маршрутов."),
            ex(
                "Going off the beaten track, we found an empty beach.",
                "Сойдя с проторённой тропы, мы нашли пустой пляж.",
            ),
        ),
        collocations = listOf(
            col("a tourist trap", "туристическая ловушка"),
            col("a hidden gem", "неизвестная жемчужина"),
        ),
    ),
    chunk(
        id = "take_a_day_trip",
        text = "take a day trip",
        translation = "съездить на однодневную экскурсию",
        headword = "trip",
        theme = TRAVEL,
        level = A2,
        frequencyBand = TOP_2000,
        pattern = "V + N",
        example = ex("We took a day trip to the coast.", "Мы съездили на побережье на один день."),
        more = listOf(
            ex(
                "There are plenty of day trips from the city.",
                "Из города можно съездить на множество однодневных экскурсий.",
            ),
            ex("It's an easy day trip by train.", "Туда легко съездить на день на поезде."),
        ),
        collocations = listOf(
            col("a round trip", "поездка туда и обратно"),
            col("a guided tour", "экскурсия с гидом"),
        ),
    ),
    chunk(
        id = "run_late",
        text = "be running late",
        translation = "опаздывать, выбиваться из графика",
        headword = "late",
        theme = TRAVEL,
        level = A2,
        frequencyBand = TOP_1000,
        register = INFORMAL,
        pattern = "be + V-ing + adj",
        example = ex("We're running late — we'll miss the train.", "Мы опаздываем — пропустим поезд."),
        more = listOf(
            ex("The flight is running two hours late.", "Рейс задерживается на два часа."),
            ex("Sorry, I'm running a bit late.", "Извини, я немного опаздываю."),
        ),
        collocations = listOf(
            col("make up time", "нагнать время"),
            col("cut it fine", "прийти впритык"),
        ),
    ),
)
