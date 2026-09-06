package by.freiding.braindrop.feature.vocabulary.data.datasource.seed

import by.freiding.braindrop.feature.vocabulary.domain.model.Chunk
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.A2
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.B1
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.B2
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkRegister.FORMAL
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkTheme.WORK
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_1000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_2000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_5000

internal val WORK_CHUNKS: List<Chunk> = listOf(
    chunk(
        id = "make_a_decision",
        text = "make a decision",
        translation = "принимать решение",
        headword = "decision",
        theme = WORK,
        level = B1,
        frequencyBand = TOP_1000,
        pattern = "V + N",
        example = ex("We need to make a decision by Friday.", "Нам нужно принять решение до пятницы."),
        more = listOf(
            ex("She made a decision to quit her job.", "Она приняла решение уйти с работы."),
            ex("Don't make such an important decision in a hurry.", "Не принимай такое важное решение в спешке."),
        ),
        error = err("do a decision", "Так не говорят — с decision идёт make, а не do."),
        collocations = listOf(
            col("reach a decision", "прийти к решению"),
            col("reverse a decision", "отменить решение"),
            col("a tough decision", "трудное решение"),
        ),
        forms = listOf(noun("decision"), verb("decide"), adj("decisive")),
        nearby = listOf("make up your mind", "come to a conclusion"),
    ),
    chunk(
        id = "meet_a_deadline",
        text = "meet a deadline",
        translation = "успеть к сроку",
        headword = "deadline",
        theme = WORK,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + N",
        example = ex("We worked all night to meet a deadline.", "Мы работали всю ночь, чтобы успеть к сроку."),
        more = listOf(
            ex(
                "If we don't meet the deadline, the client will be upset.",
                "Если мы не уложимся в срок, клиент расстроится.",
            ),
            ex("He always meets his deadlines.", "Он всегда сдаёт работу в срок."),
        ),
        error = err("make a deadline", "С deadline используется meet, а не make."),
        collocations = listOf(
            col("miss a deadline", "сорвать срок"),
            col("a tight deadline", "жёсткий срок"),
            col("extend a deadline", "продлить срок"),
        ),
        nearby = listOf("be on schedule", "run out of time"),
    ),
    chunk(
        id = "take_responsibility_for",
        text = "take responsibility for",
        translation = "брать ответственность за",
        headword = "responsibility",
        theme = WORK,
        level = B2,
        register = FORMAL,
        pattern = "V + N + prep",
        example = ex(
            "Someone has to take responsibility for the mistake.",
            "Кто-то должен взять ответственность за эту ошибку.",
        ),
        more = listOf(
            ex(
                "She took full responsibility for the failed project.",
                "Она взяла на себя всю ответственность за провал проекта.",
            ),
            ex(
                "No one wanted to take responsibility for the decision.",
                "Никто не хотел брать ответственность за это решение.",
            ),
        ),
        collocations = listOf(
            col("shoulder the responsibility", "взвалить на себя ответственность"),
            col("shirk responsibility", "уклоняться от ответственности"),
            col("a heavy responsibility", "тяжёлая ответственность"),
        ),
        forms = listOf(noun("responsibility"), adj("responsible")),
        nearby = listOf("take the blame", "be accountable for"),
    ),
    chunk(
        id = "attend_a_meeting",
        text = "attend a meeting",
        translation = "присутствовать на встрече",
        headword = "meeting",
        theme = WORK,
        level = B1,
        frequencyBand = TOP_2000,
        register = FORMAL,
        pattern = "V + N",
        example = ex("I can't attend the meeting tomorrow.", "Я не смогу присутствовать на встрече завтра."),
        more = listOf(
            ex(
                "All team leads must attend the weekly meeting.",
                "Все руководители групп обязаны посещать еженедельную встречу.",
            ),
            ex("She attended the meeting by video call.", "Она участвовала во встрече по видеосвязи."),
        ),
        error = err("visit a meeting", "На встречу не «visit» — по-английски meetings attend."),
        collocations = listOf(
            col("hold a meeting", "проводить встречу"),
            col("chair a meeting", "вести встречу"),
            col("call off a meeting", "отменить встречу"),
        ),
        forms = listOf(verb("attend"), noun("attendance")),
    ),
    chunk(
        id = "give_a_presentation",
        text = "give a presentation",
        translation = "выступать с презентацией",
        headword = "presentation",
        theme = WORK,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + N",
        example = ex("I have to give a presentation on Monday.", "В понедельник мне нужно выступить с презентацией."),
        more = listOf(
            ex(
                "She gave a great presentation to the board.",
                "Она отлично выступила с презентацией перед советом директоров.",
            ),
            ex(
                "He gets nervous every time he gives a presentation.",
                "Он нервничает каждый раз, когда выступает с презентацией.",
            ),
        ),
        error = err("tell a presentation", "Презентацию не «tell» — её give (или do)."),
        collocations = listOf(
            col("prepare a presentation", "готовить презентацию"),
            col("a slide in the presentation", "слайд в презентации"),
        ),
        nearby = listOf("give a talk", "make a speech"),
    ),
    chunk(
        id = "run_a_business",
        text = "run a business",
        translation = "вести бизнес",
        headword = "business",
        theme = WORK,
        level = B1,
        frequencyBand = TOP_1000,
        pattern = "V + N",
        example = ex("They run a small business together.", "Они вместе ведут небольшой бизнес."),
        more = listOf(
            ex("It's hard to run a business during a recession.", "Тяжело вести бизнес во время кризиса."),
            ex("She runs her own catering business.", "Она ведёт собственный кейтеринговый бизнес."),
        ),
        collocations = listOf(
            col("start a business", "открыть бизнес"),
            col("a family business", "семейный бизнес"),
            col("do business with", "вести дела с"),
        ),
    ),
    chunk(
        id = "apply_for_a_job",
        text = "apply for a job",
        translation = "подавать заявку на работу",
        headword = "job",
        theme = WORK,
        level = A2,
        frequencyBand = TOP_1000,
        pattern = "V + prep + N",
        example = ex("I want to apply for a job at that company.", "Я хочу подать заявку на работу в ту компанию."),
        more = listOf(
            ex("She applied for three jobs last week.", "На прошлой неделе она откликнулась на три вакансии."),
            ex("You should apply for the job before the deadline.", "Стоит подать заявку на эту работу до дедлайна."),
        ),
        error = err("apply to a job", "На вакансию — apply for a job; apply to используется с компанией."),
        collocations = listOf(
            col("get a job", "получить работу"),
            col("quit a job", "уволиться с работы"),
            col("a full-time job", "работа на полную ставку"),
        ),
        forms = listOf(verb("apply"), noun("application"), noun("applicant")),
    ),
    chunk(
        id = "work_overtime",
        text = "work overtime",
        translation = "работать сверхурочно",
        headword = "overtime",
        theme = WORK,
        level = B1,
        frequencyBand = TOP_5000,
        pattern = "V + adv",
        example = ex(
            "I had to work overtime three days this week.",
            "На этой неделе мне пришлось работать сверхурочно три дня.",
        ),
        more = listOf(
            ex("They don't pay us for working overtime.", "Нам не платят за сверхурочную работу."),
            ex("She's been working overtime to finish the report.", "Она работает сверхурочно, чтобы закончить отчёт."),
        ),
        collocations = listOf(
            col("do overtime", "перерабатывать"),
            col("paid overtime", "оплачиваемые переработки"),
        ),
    ),
    chunk(
        id = "take_a_day_off",
        text = "take a day off",
        translation = "взять выходной",
        headword = "day off",
        theme = WORK,
        level = A2,
        frequencyBand = TOP_2000,
        pattern = "V + N",
        example = ex("I'm going to take a day off on Friday.", "В пятницу я возьму выходной."),
        more = listOf(
            ex("She took a day off to go to the doctor.", "Она взяла отгул, чтобы сходить к врачу."),
            ex("Can I take tomorrow off?", "Можно я завтра не выйду на работу?"),
        ),
        error = err("take a free day", "По-английски это a day off, не «a free day»."),
        collocations = listOf(
            col("a day off work", "выходной от работы"),
            col("book a day off", "оформить отгул"),
        ),
        nearby = listOf("call in sick", "be on leave"),
    ),
    chunk(
        id = "meet_a_target",
        text = "meet a target",
        translation = "достичь плановых показателей",
        headword = "target",
        theme = WORK,
        level = B2,
        frequencyBand = TOP_2000,
        register = FORMAL,
        pattern = "V + N",
        example = ex(
            "The team met its sales target this quarter.",
            "В этом квартале команда выполнила план по продажам.",
        ),
        more = listOf(
            ex("We're on track to meet the target.", "Мы идём по графику к достижению цели."),
            ex("If we don't meet the target, bonuses will be cut.", "Если не выполним план, урежут премии."),
        ),
        collocations = listOf(
            col("set a target", "поставить цель"),
            col("hit a target", "попасть в цель"),
            col("miss a target", "не достичь показателя"),
        ),
    ),
    chunk(
        id = "sign_a_contract",
        text = "sign a contract",
        translation = "подписать договор",
        headword = "contract",
        theme = WORK,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + N",
        example = ex(
            "We're going to sign a contract with the supplier.",
            "Мы собираемся подписать договор с поставщиком.",
        ),
        more = listOf(
            ex("She signed a two-year contract with the club.", "Она подписала двухлетний контракт с клубом."),
            ex(
                "Read the contract carefully before you sign it.",
                "Внимательно прочитай договор, прежде чем подписывать.",
            ),
        ),
        collocations = listOf(
            col("break a contract", "нарушить договор"),
            col("renew a contract", "продлить договор"),
            col("a fixed-term contract", "срочный договор"),
        ),
    ),
    chunk(
        id = "get_promoted",
        text = "get promoted",
        translation = "получить повышение",
        headword = "promoted",
        theme = WORK,
        level = B1,
        frequencyBand = TOP_5000,
        pattern = "V + adj",
        example = ex("He hopes to get promoted next year.", "В следующем году он надеется получить повышение."),
        more = listOf(
            ex("She got promoted to team lead after two years.", "Через два года её повысили до тимлида."),
            ex("You won't get promoted if you never speak up.", "Тебя не повысят, если ты никогда не высказываешься."),
        ),
        collocations = listOf(
            col("apply for promotion", "претендовать на повышение"),
            col("a well-deserved promotion", "заслуженное повышение"),
        ),
        forms = listOf(verb("promote"), noun("promotion")),
    ),
    chunk(
        id = "deal_with_a_complaint",
        text = "deal with a complaint",
        translation = "разбираться с жалобой",
        headword = "complaint",
        theme = WORK,
        level = B2,
        frequencyBand = TOP_2000,
        pattern = "V + prep + N",
        example = ex(
            "Part of my job is to deal with customer complaints.",
            "Часть моей работы — разбираться с жалобами клиентов.",
        ),
        more = listOf(
            ex("She dealt with the complaint calmly and quickly.", "Она разобралась с жалобой спокойно и быстро."),
            ex("We take every complaint seriously.", "Мы серьёзно относимся к каждой жалобе."),
        ),
        collocations = listOf(
            col("file a complaint", "подать жалобу"),
            col("a formal complaint", "официальная жалоба"),
            col("look into a complaint", "рассмотреть жалобу"),
        ),
        forms = listOf(noun("complaint"), verb("complain")),
    ),
)
