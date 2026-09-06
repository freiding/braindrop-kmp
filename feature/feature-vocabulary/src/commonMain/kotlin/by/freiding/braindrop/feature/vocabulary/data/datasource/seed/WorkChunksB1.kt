package by.freiding.braindrop.feature.vocabulary.data.datasource.seed

import by.freiding.braindrop.feature.vocabulary.domain.model.Chunk
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.B1
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkRegister.INFORMAL
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkTheme.WORK
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_1000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_2000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_5000

/** B1-level content drop for the Work theme. */
internal val WORK_CHUNKS_B1: List<Chunk> = listOf(
    chunk(
        id = "take_on_a_project",
        text = "take on a project",
        translation = "взяться за проект",
        headword = "project",
        theme = WORK,
        level = B1,
        frequencyBand = TOP_1000,
        pattern = "V + adv + N",
        example = ex("She took on a big project last month.", "В прошлом месяце она взялась за крупный проект."),
        more = listOf(
            ex("Don't take on too much at once.", "Не бери на себя слишком много сразу."),
            ex("We can't take on any new projects right now.", "Сейчас мы не можем брать новые проекты."),
        ),
        error = err("take a project on yourself", "Естественнее: take on a project."),
        collocations = listOf(
            col("hand over a project", "передать проект"),
            col("scope out a project", "оценить объём проекта"),
        ),
    ),
    chunk(
        id = "go_over_the_figures",
        text = "go over the figures",
        translation = "просмотреть цифры, проверить расчёты",
        headword = "figures",
        theme = WORK,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + prep + N",
        example = ex("Let's go over the figures before the meeting.", "Давай просмотрим цифры перед совещанием."),
        more = listOf(
            ex("I went over the numbers twice.", "Я дважды проверил расчёты."),
            ex("Can you go over the budget with me?", "Разберём бюджет вместе?"),
        ),
        collocations = listOf(
            col("crunch the numbers", "прогнать цифры"),
            col("double-check the totals", "перепроверить итоги"),
        ),
    ),
    chunk(
        id = "chase_up_a_reply",
        text = "chase up a reply",
        translation = "напомнить об ответе, поторопить",
        headword = "chase",
        theme = WORK,
        level = B1,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + adv + N",
        example = ex("I'll chase up a reply from the supplier.", "Я потороплю поставщика с ответом."),
        more = listOf(
            ex("Can you chase them up about the invoice?", "Можешь напомнить им про счёт?"),
            ex("She had to chase up the payment twice.", "Ей дважды пришлось напоминать об оплате."),
        ),
        collocations = listOf(
            col("follow up on an email", "напомнить письмом"),
            col("send a reminder", "отправить напоминание"),
        ),
    ),
    chunk(
        id = "run_behind_schedule",
        text = "run behind schedule",
        translation = "выбиваться из графика",
        headword = "schedule",
        theme = WORK,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + prep + N",
        example = ex("The project is running behind schedule.", "Проект выбивается из графика."),
        more = listOf(
            ex("We're two weeks behind schedule.", "Мы отстаём от графика на две недели."),
            ex("If we run behind, we'll have to cut features.", "Если отстанем, придётся урезать функции."),
        ),
        error = err("be late from the schedule", "Устойчиво: run / be behind schedule."),
        collocations = listOf(
            col("be ahead of schedule", "опережать график"),
            col("catch up on lost time", "нагнать упущенное время"),
        ),
    ),
    chunk(
        id = "get_to_grips_with_a_task",
        text = "get to grips with a task",
        translation = "разобраться в задаче, освоиться",
        headword = "grips",
        theme = WORK,
        level = B1,
        frequencyBand = TOP_5000,
        pattern = "V + prep + N + prep + N",
        example = ex(
            "It took me a week to get to grips with the new system.",
            "Мне понадобилась неделя, чтобы освоить новую систему.",
        ),
        more = listOf(
            ex("She quickly got to grips with the role.", "Она быстро вникла в новую должность."),
            ex("Once you get to grips with it, it's simple.", "Как только разберёшься, всё просто."),
        ),
        collocations = listOf(
            col("find your feet", "освоиться на новом месте"),
            col("get up to speed", "войти в курс дела"),
        ),
    ),
    chunk(
        id = "cover_for_a_colleague",
        text = "cover for a colleague",
        translation = "подменять коллегу",
        headword = "cover",
        theme = WORK,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + prep + N",
        example = ex("Could you cover for me on Friday?", "Ты не подменишь меня в пятницу?"),
        more = listOf(
            ex("She's covering for someone on maternity leave.", "Она замещает сотрудницу в декрете."),
            ex("Thanks for covering for me while I was away.", "Спасибо, что подменял меня, пока меня не было."),
        ),
        error = err("cover a colleague instead", "«Подменять» — cover for someone."),
        collocations = listOf(
            col("fill in for someone", "заменить кого-то"),
            col("hold the fort", "остаться за старшего"),
        ),
    ),
    chunk(
        id = "wrap_up_a_project",
        text = "wrap up a project",
        translation = "завершить проект",
        headword = "wrap",
        theme = WORK,
        level = B1,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + adv + N",
        example = ex("We're aiming to wrap up the project by June.", "Мы рассчитываем завершить проект к июню."),
        more = listOf(
            ex("Let's wrap this up — it's almost six.", "Давай закругляться — уже почти шесть."),
            ex("They wrapped up the deal in a week.", "Сделку они закрыли за неделю."),
        ),
        collocations = listOf(
            col("tie up loose ends", "довести до конца мелочи"),
            col("sign off on something", "утвердить, поставить точку"),
        ),
    ),
    chunk(
        id = "put_in_a_good_word",
        text = "put in a good word for someone",
        translation = "замолвить за кого-то словечко",
        headword = "word",
        theme = WORK,
        level = B1,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + adv + adj + N + prep + N",
        example = ex(
            "She put in a good word for me with the manager.",
            "Она замолвила за меня словечко перед менеджером.",
        ),
        more = listOf(
            ex("Could you put in a good word for my application?", "Не мог бы ты замолвить словечко за мою заявку?"),
            ex(
                "He got the job after a colleague put in a good word.",
                "Он получил работу после того, как коллега за него замолвил слово.",
            ),
        ),
        collocations = listOf(
            col("vouch for someone", "поручиться за кого-то"),
            col("recommend someone for a role", "рекомендовать кого-то на должность"),
        ),
    ),
)
