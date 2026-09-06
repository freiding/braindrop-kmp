package by.freiding.braindrop.feature.vocabulary.data.datasource.seed

import by.freiding.braindrop.feature.vocabulary.domain.model.Chunk
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.B1
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkRegister.INFORMAL
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkTheme.EDUCATION
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_1000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_2000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_5000

/** B1-level content drop for the Education theme. */
internal val EDUCATION_CHUNKS_B1: List<Chunk> = listOf(
    chunk(
        id = "go_over_your_notes",
        text = "go over your notes",
        translation = "просмотреть конспект",
        headword = "notes",
        theme = EDUCATION,
        level = B1,
        frequencyBand = TOP_1000,
        pattern = "V + prep + poss + N",
        example = ex("Go over your notes the evening before the exam.", "Просмотри конспект вечером перед экзаменом."),
        more = listOf(
            ex("We went over the notes together before the test.", "Перед тестом мы вместе просмотрели конспект."),
            ex("She goes over her notes on the train.", "Она повторяет конспект в поезде."),
        ),
        collocations = listOf(
            col("look back over the material", "повторить материал"),
            col("highlight the key points", "выделить главное"),
        ),
    ),
    chunk(
        id = "work_through_an_exercise",
        text = "work through an exercise",
        translation = "проработать упражнение",
        headword = "exercise",
        theme = EDUCATION,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + prep + N",
        example = ex("Let's work through this exercise step by step.", "Давай разберём это упражнение шаг за шагом."),
        more = listOf(
            ex("He worked through every problem in the book.", "Он прорешал все задачи в учебнике."),
            ex("Working through examples helps it stick.", "Разбор примеров помогает запомнить."),
        ),
        collocations = listOf(
            col("do a worksheet", "выполнить рабочий лист"),
            col("check your answers", "проверить ответы"),
        ),
    ),
    chunk(
        id = "jot_something_down",
        text = "jot something down",
        translation = "быстро записать, набросать",
        headword = "jot",
        theme = EDUCATION,
        level = B1,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + N + adv",
        example = ex("Jot down any questions as they come up.", "Записывай вопросы по мере появления."),
        more = listOf(
            ex("She jotted down his number on a napkin.", "Она записала его номер на салфетке."),
            ex("Let me jot that down before I forget.", "Дай запишу, пока не забыл."),
        ),
        collocations = listOf(
            col("make a quick note", "черкнуть заметку"),
            col("scribble something down", "нацарапать что-то"),
        ),
    ),
    chunk(
        id = "stay_behind_after_class",
        text = "stay behind after class",
        translation = "остаться после урока",
        headword = "class",
        theme = EDUCATION,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + adv + prep + N",
        example = ex(
            "I stayed behind after class to ask the teacher.",
            "Я остался после урока, чтобы спросить учителя.",
        ),
        more = listOf(
            ex("Two students stayed behind to help tidy up.", "Двое учеников задержались, чтобы помочь с уборкой."),
            ex("Can you stay behind for a minute?", "Задержись, пожалуйста, на минуту."),
        ),
        collocations = listOf(
            col("get extra help", "получить дополнительную помощь"),
            col("a one-to-one session", "индивидуальное занятие"),
        ),
    ),
    chunk(
        id = "set_aside_time_to_study",
        text = "set aside time to study",
        translation = "выделить время на учёбу",
        headword = "time",
        theme = EDUCATION,
        level = B1,
        frequencyBand = TOP_1000,
        pattern = "V + adv + N + to + V",
        example = ex("Set aside an hour a day to study.", "Выделяй час в день на учёбу."),
        more = listOf(
            ex(
                "She sets aside every Sunday morning for revision.",
                "Каждое воскресное утро она отводит на повторение.",
            ),
            ex("If you don't set aside time, it won't happen.", "Если не выделишь время, ничего не выйдет."),
        ),
        collocations = listOf(
            col("stick to a study plan", "придерживаться плана занятий"),
            col("block out time", "зарезервировать время"),
        ),
    ),
    chunk(
        id = "break_the_material_down",
        text = "break the material down",
        translation = "разбить материал на части",
        headword = "material",
        theme = EDUCATION,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + N + adv",
        example = ex(
            "The teacher broke the material down into small steps.",
            "Учитель разбил материал на маленькие шаги.",
        ),
        more = listOf(
            ex("Break it down and it's much less scary.", "Разбей на части — и станет не так страшно."),
            ex(
                "She breaks each topic down before an exam.",
                "Перед экзаменом она раскладывает каждую тему по полочкам.",
            ),
        ),
        error = err("split the material to parts", "«Разбить на части» — break something down into parts."),
        collocations = listOf(
            col("get your head around something", "уложить в голове"),
            col("take it one step at a time", "по шагам"),
        ),
    ),
    chunk(
        id = "hand_your_work_in_on_time",
        text = "hand your work in on time",
        translation = "сдать работу в срок",
        headword = "work",
        theme = EDUCATION,
        level = B1,
        frequencyBand = TOP_1000,
        pattern = "V + poss + N + adv + prep + N",
        example = ex("Make sure you hand your work in on time.", "Обязательно сдай работу в срок."),
        more = listOf(
            ex("He never hands his work in on time.", "Он никогда не сдаёт работу вовремя."),
            ex("Hand it in a day early if you can.", "Сдай на день раньше, если получится."),
        ),
        collocations = listOf(
            col("miss the deadline", "просрочить сдачу"),
            col("ask for an extension", "попросить продление"),
        ),
    ),
    chunk(
        id = "test_yourself",
        text = "test yourself",
        translation = "проверять себя",
        headword = "test",
        theme = EDUCATION,
        level = B1,
        frequencyBand = TOP_1000,
        pattern = "V + pron",
        example = ex(
            "Test yourself with flashcards after each lesson.",
            "Проверяй себя карточками после каждого урока.",
        ),
        more = listOf(
            ex(
                "Testing yourself is better than just re-reading.",
                "Самопроверка эффективнее, чем просто перечитывание.",
            ),
            ex("She tests herself before she tests the class.", "Сначала она проверяет себя, потом класс."),
        ),
        collocations = listOf(
            col("do a practice test", "пройти пробный тест"),
            col("quiz yourself", "устроить себе опрос"),
        ),
    ),
)
