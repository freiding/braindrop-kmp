package by.freiding.braindrop.feature.tenses.data.datasource

import by.freiding.braindrop.feature.tenses.domain.model.Tense
import by.freiding.braindrop.feature.tenses.domain.model.TenseAspect
import by.freiding.braindrop.feature.tenses.domain.model.TenseComparison
import by.freiding.braindrop.feature.tenses.domain.model.TenseExample
import by.freiding.braindrop.feature.tenses.domain.model.TenseFormExample
import by.freiding.braindrop.feature.tenses.domain.model.TenseFormulas
import by.freiding.braindrop.feature.tenses.domain.model.TenseScenario
import by.freiding.braindrop.feature.tenses.domain.model.TenseTime
import by.freiding.braindrop.feature.tenses.domain.model.TenseUsageCase

class LocalTenseDataSource {
    fun getTenses(): List<Tense> = TENSES

    fun getById(id: String): Tense? = TENSES.firstOrNull { it.id == id }

    fun getComparisons(): List<TenseComparison> = COMPARISONS

    fun getComparison(id: String): TenseComparison? = COMPARISONS.firstOrNull { it.id == id }
}

// Content lives at file scope rather than in a companion object so it isn't counted as part of
// LocalTenseDataSource's class size by static analysis — this is data, not class complexity.
private val TENSES =
    listOf(
        tense(
            id = "present_simple",
            time = TenseTime.PRESENT,
            aspect = TenseAspect.SIMPLE,
            titleEn = "Present Simple",
            titleRu = "Настоящее простое",
            formulas = TenseFormulas(
                affirmative = "S + V(s/es)",
                negative = "S + do/does + not + V",
                question = "Do/Does + S + V?",
            ),
            scenarios = mapOf(
                TenseScenario.READ_BOOK to TenseFormExample(
                    "She reads a book every evening.",
                    "Она читает книгу каждый вечер.",
                    "reads",
                ),
                TenseScenario.CALL_FRIEND to TenseFormExample(
                    "We call our friend every Sunday.",
                    "Мы звоним другу каждое воскресенье.",
                    "call",
                ),
                TenseScenario.FINISH_PROJECT to TenseFormExample(
                    "They finish the project every year in June.",
                    "Они завершают проект каждый год в июне.",
                    "finish",
                ),
                TenseScenario.TRAVEL to TenseFormExample(
                    "He travels to Paris every summer.",
                    "Он ездит в Париж каждое лето.",
                    "travels",
                ),
                TenseScenario.STUDY to TenseFormExample(
                    "They study English at school.",
                    "Они изучают английский в школе.",
                    "study",
                ),
            ),
            usageCases = listOf(
                TenseUsageCase(
                    "Постоянные факты и общеизвестные истины",
                    TenseExample("Water boils at 100 degrees.", "Вода кипит при 100 градусах."),
                ),
                TenseUsageCase(
                    "Привычки и регулярные действия",
                    TenseExample(
                        "He goes to the gym three times a week.",
                        "Он ходит в спортзал три раза в неделю.",
                    ),
                ),
                TenseUsageCase(
                    "Расписания и таймтейблы",
                    TenseExample("The train leaves at 6 p.m.", "Поезд отправляется в 18:00."),
                ),
                TenseUsageCase(
                    "Последовательность действий по шагам",
                    TenseExample(
                        "First you open the app, then you log in.",
                        "Сначала ты открываешь приложение, потом входишь в систему.",
                    ),
                ),
            ),
            markers = listOf("always", "usually", "often", "sometimes", "rarely", "never", "every day"),
            markerExamples = listOf(
                TenseExample("I always wake up at 7 a.m.", "Я всегда просыпаюсь в 7 утра."),
                TenseExample("She rarely eats fast food.", "Она редко ест фастфуд."),
            ),
            commonMistake = "Часто путают с Present Continuous и используют V-ing для привычных действий: " +
                "✗ I am working every day вместо ✓ I work every day. В русском нет отдельной формы для " +
                "привычных и текущих действий, поэтому обе конструкции кажутся естественными.",
            confusedWith = listOf("present_continuous", "present_perfect"),
            specialNotes = listOf(
                TenseUsageCase(
                    "Глаголы состояния (know, like, love, believe, want, understand) не используются в " +
                        "Continuous даже для момента речи — только Present Simple.",
                    TenseExample("I understand you now.", "Я понимаю тебя сейчас."),
                ),
                TenseUsageCase(
                    "В придаточных времени и условия после when, if, before, after, as soon as, until " +
                        "используется Present Simple, а не Future Simple.",
                    TenseExample("I will call you when I arrive.", "Я позвоню тебе, когда приеду."),
                ),
            ),
        ),
        tense(
            id = "present_continuous",
            time = TenseTime.PRESENT,
            aspect = TenseAspect.CONTINUOUS,
            titleEn = "Present Continuous",
            titleRu = "Настоящее продолженное",
            formulas = TenseFormulas(
                affirmative = "S + am/is/are + V-ing",
                negative = "S + am/is/are + not + V-ing",
                question = "Am/Is/Are + S + V-ing?",
            ),
            scenarios = mapOf(
                TenseScenario.READ_BOOK to TenseFormExample(
                    "She is reading a book right now.",
                    "Она читает книгу прямо сейчас.",
                    "is reading",
                ),
                TenseScenario.CALL_FRIEND to TenseFormExample(
                    "We are calling our friend right now.",
                    "Мы звоним другу прямо сейчас.",
                    "are calling",
                ),
                TenseScenario.FINISH_PROJECT to TenseFormExample(
                    "They are finishing the project right now.",
                    "Они заканчивают проект прямо сейчас.",
                    "are finishing",
                ),
                TenseScenario.TRAVEL to TenseFormExample(
                    "He is traveling around Europe right now.",
                    "Он путешествует по Европе прямо сейчас.",
                    "is traveling",
                ),
                TenseScenario.STUDY to TenseFormExample(
                    "They are studying English right now.",
                    "Они изучают английский прямо сейчас.",
                    "are studying",
                ),
            ),
            usageCases = listOf(
                TenseUsageCase(
                    "Действие происходит прямо сейчас",
                    TenseExample("I am writing an email.", "Я пишу письмо прямо сейчас."),
                ),
                TenseUsageCase(
                    "Временная ситуация вокруг текущего момента",
                    TenseExample(
                        "She is studying for her exams this week.",
                        "Она готовится к экзаменам на этой неделе.",
                    ),
                ),
                TenseUsageCase(
                    "Запланированное действие в ближайшем будущем",
                    TenseExample("We are meeting him tomorrow.", "Мы встречаемся с ним завтра."),
                ),
                TenseUsageCase(
                    "Изменяющаяся, развивающаяся ситуация",
                    TenseExample("The climate is changing rapidly.", "Климат стремительно меняется."),
                ),
            ),
            markers = listOf("now", "right now", "at the moment", "at present", "currently", "still"),
            markerExamples = listOf(
                TenseExample(
                    "Please be quiet, the baby is sleeping right now.",
                    "Пожалуйста, тише, малыш сейчас спит.",
                ),
                TenseExample("He is currently working on a new project.", "Сейчас он работает над новым проектом."),
            ),
            commonMistake = "Забывают, что глаголы состояния (know, like, want, believe, love) обычно не " +
                "используются в Continuous: ✗ I am knowing him вместо ✓ I know him.",
            confusedWith = listOf("present_simple", "present_perfect_continuous"),
            specialNotes = listOf(
                TenseUsageCase(
                    "Некоторые «глаголы состояния» всё же допускают Continuous, если описывают процесс, " +
                        "а не постоянное состояние: think как мнение не изменяется, а как размышление — да.",
                    TenseExample("I'm thinking about your offer.", "Я обдумываю твоё предложение."),
                ),
            ),
        ),
        tense(
            id = "present_perfect",
            time = TenseTime.PRESENT,
            aspect = TenseAspect.PERFECT,
            titleEn = "Present Perfect",
            titleRu = "Настоящее совершенное",
            formulas = TenseFormulas(
                affirmative = "S + have/has + V3",
                negative = "S + have/has + not + V3",
                question = "Have/Has + S + V3?",
            ),
            scenarios = mapOf(
                TenseScenario.READ_BOOK to TenseFormExample(
                    "She has read a book this month.",
                    "Она прочитала книгу в этом месяце.",
                    "has read",
                ),
                TenseScenario.CALL_FRIEND to TenseFormExample(
                    "We have already called our friend today.",
                    "Мы уже позвонили другу сегодня.",
                    "have already called",
                ),
                TenseScenario.FINISH_PROJECT to TenseFormExample(
                    "They have finished the project already.",
                    "Они уже завершили проект.",
                    "have finished",
                ),
                TenseScenario.TRAVEL to TenseFormExample(
                    "He has traveled to ten countries.",
                    "Он уже побывал в десяти странах.",
                    "has traveled",
                ),
                TenseScenario.STUDY to TenseFormExample(
                    "They have studied three languages.",
                    "Они изучили три языка.",
                    "have studied",
                ),
            ),
            usageCases = listOf(
                TenseUsageCase(
                    "Действие в неопределённом прошлом, но важен результат сейчас",
                    TenseExample("I have lost my keys.", "Я потерял ключи (и сейчас их у меня нет)."),
                ),
                TenseUsageCase(
                    "Опыт в жизни — когда-либо/никогда",
                    TenseExample("Have you ever been to Japan?", "Ты когда-нибудь был в Японии?"),
                ),
                TenseUsageCase(
                    "Действие началось в прошлом и продолжается по сей день",
                    TenseExample("She has lived here for ten years.", "Она живёт здесь уже десять лет."),
                ),
                TenseUsageCase(
                    "Недавно завершённое действие",
                    TenseExample("They have just arrived.", "Они только что приехали."),
                ),
            ),
            markers = listOf("already", "just", "yet", "ever", "never", "since", "for", "so far", "recently"),
            markerExamples = listOf(
                TenseExample("I have already finished my homework.", "Я уже закончил домашнее задание."),
                TenseExample("Have you finished the report yet?", "Ты уже закончил отчёт?"),
            ),
            commonMistake = "Самая частая ошибка — использование конкретного времени в прошлом с Present " +
                "Perfect: ✗ I have seen him yesterday вместо ✓ I saw him yesterday. В русском нет аналога " +
                "Present Perfect, поэтому связь с настоящим моментом не улавливается интуитивно.",
            confusedWith = listOf("past_simple", "present_perfect_continuous"),
            specialNotes = listOf(
                TenseUsageCase(
                    "have been to (съездил и вернулся) отличается от have gone to (уехал и ещё не вернулся).",
                    TenseExample("She has been to Rome twice.", "Она дважды была в Риме (и сейчас не там)."),
                ),
                TenseUsageCase(
                    "В американском варианте английского Past Simple неформально заменяет Present Perfect " +
                        "с already/just/yet — в британском это считается нестандартным.",
                    TenseExample("I already ate.", "Я уже поел (разговорный американский вариант)."),
                ),
            ),
        ),
        tense(
            id = "present_perfect_continuous",
            time = TenseTime.PRESENT,
            aspect = TenseAspect.PERFECT_CONTINUOUS,
            titleEn = "Present Perfect Continuous",
            titleRu = "Настоящее совершенное продолженное",
            formulas = TenseFormulas(
                affirmative = "S + have/has + been + V-ing",
                negative = "S + have/has + not + been + V-ing",
                question = "Have/Has + S + been + V-ing?",
            ),
            scenarios = mapOf(
                TenseScenario.READ_BOOK to TenseFormExample(
                    "She has been reading a book for two hours.",
                    "Она уже два часа читает книгу.",
                    "has been reading",
                ),
                TenseScenario.CALL_FRIEND to TenseFormExample(
                    "We have been calling our friend all morning.",
                    "Мы уже всё утро звоним другу.",
                    "have been calling",
                ),
                TenseScenario.FINISH_PROJECT to TenseFormExample(
                    "They have been finishing the project all week.",
                    "Они уже неделю заканчивают проект.",
                    "have been finishing",
                ),
                TenseScenario.TRAVEL to TenseFormExample(
                    "He has been traveling for two months.",
                    "Он путешествует уже два месяца.",
                    "has been traveling",
                ),
                TenseScenario.STUDY to TenseFormExample(
                    "They have been studying English for five years.",
                    "Они изучают английский уже пять лет.",
                    "have been studying",
                ),
            ),
            usageCases = listOf(
                TenseUsageCase(
                    "Действие длится некоторое время и всё ещё продолжается",
                    TenseExample("I have been learning English for three years.", "Я учу английский уже три года."),
                ),
                TenseUsageCase(
                    "Действие недавно закончилось, но его результат виден сейчас",
                    TenseExample("You look tired — have you been running?", "Ты выглядишь уставшим — ты бегал?"),
                ),
                TenseUsageCase(
                    "Раздражение или эмоциональная окраска по поводу процесса",
                    TenseExample("Who has been eating my sandwich?", "Кто ел мой бутерброд?"),
                ),
            ),
            markers = listOf("for", "since", "all day", "all week", "lately", "recently", "how long"),
            markerExamples = listOf(
                TenseExample(
                    "We have been waiting for the bus for twenty minutes.",
                    "Мы ждём автобус уже двадцать минут.",
                ),
                TenseExample("I have been feeling tired lately.", "Последнее время я чувствую усталость."),
            ),
            commonMistake = "Путают с Present Perfect, когда важен факт результата, а не длительность " +
                "процесса: ✗ I have been reading this book (если книга уже прочитана целиком) вместо " +
                "✓ I have read this book.",
            confusedWith = listOf("present_perfect", "present_continuous"),
            specialNotes = listOf(
                TenseUsageCase(
                    "С глаголами состояния используется обычный Present Perfect, а не Continuous.",
                    TenseExample("I have known him since childhood.", "Я знаю его с детства."),
                ),
            ),
        ),
        tense(
            id = "past_simple",
            time = TenseTime.PAST,
            aspect = TenseAspect.SIMPLE,
            titleEn = "Past Simple",
            titleRu = "Прошедшее простое",
            formulas = TenseFormulas(
                affirmative = "S + V2 (V-ed)",
                negative = "S + did + not + V",
                question = "Did + S + V?",
            ),
            scenarios = mapOf(
                TenseScenario.READ_BOOK to TenseFormExample(
                    "She read a book yesterday.",
                    "Она читала книгу вчера.",
                    "read",
                ),
                TenseScenario.CALL_FRIEND to TenseFormExample(
                    "We called our friend yesterday.",
                    "Мы звонили другу вчера.",
                    "called",
                ),
                TenseScenario.FINISH_PROJECT to TenseFormExample(
                    "They finished the project last month.",
                    "Они завершили проект в прошлом месяце.",
                    "finished",
                ),
                TenseScenario.TRAVEL to TenseFormExample(
                    "He traveled to Paris last summer.",
                    "Он ездил в Париж прошлым летом.",
                    "traveled",
                ),
                TenseScenario.STUDY to TenseFormExample(
                    "They studied English last year.",
                    "Они изучали английский в прошлом году.",
                    "studied",
                ),
            ),
            usageCases = listOf(
                TenseUsageCase(
                    "Завершённое действие в конкретный момент в прошлом",
                    TenseExample("I visited Paris in 2019.", "Я посетил Париж в 2019 году."),
                ),
                TenseUsageCase(
                    "Последовательность завершённых действий",
                    TenseExample(
                        "She woke up, made coffee and left for work.",
                        "Она проснулась, сварила кофе и ушла на работу.",
                    ),
                ),
                TenseUsageCase(
                    "Привычка в прошлом, которой больше нет",
                    TenseExample(
                        "He played football every weekend when he was young.",
                        "В молодости он играл в футбол каждые выходные.",
                    ),
                ),
            ),
            markers = listOf("yesterday", "last week", "last month", "last year", "ago", "in 2015", "when"),
            markerExamples = listOf(
                TenseExample("We watched a movie yesterday.", "Мы посмотрели фильм вчера."),
                TenseExample(
                    "They moved to this city two years ago.",
                    "Они переехали в этот город два года назад.",
                ),
            ),
            commonMistake = "Используют Present Perfect там, где есть явный маркер конкретного момента в " +
                "прошлом (yesterday, in 2010, last year) — это обратная ошибка к Present Perfect.",
            confusedWith = listOf("present_perfect", "past_continuous"),
            specialNotes = listOf(
                TenseUsageCase(
                    "Для повторяющихся действий или состояний в прошлом, которых больше нет, часто " +
                        "используют конструкцию used to вместо простого Past Simple.",
                    TenseExample("I used to play the piano.", "Раньше я играл на пианино (сейчас не играю)."),
                ),
            ),
        ),
        tense(
            id = "past_continuous",
            time = TenseTime.PAST,
            aspect = TenseAspect.CONTINUOUS,
            titleEn = "Past Continuous",
            titleRu = "Прошедшее продолженное",
            formulas = TenseFormulas(
                affirmative = "S + was/were + V-ing",
                negative = "S + was/were + not + V-ing",
                question = "Was/Were + S + V-ing?",
            ),
            scenarios = mapOf(
                TenseScenario.READ_BOOK to TenseFormExample(
                    "She was reading a book when the phone rang.",
                    "Она читала книгу, когда зазвонил телефон.",
                    "was reading",
                ),
                TenseScenario.CALL_FRIEND to TenseFormExample(
                    "We were calling our friend when the doorbell rang.",
                    "Мы звонили другу, когда прозвенел дверной звонок.",
                    "were calling",
                ),
                TenseScenario.FINISH_PROJECT to TenseFormExample(
                    "They were finishing the project when the client called.",
                    "Они заканчивали проект, когда позвонил клиент.",
                    "were finishing",
                ),
                TenseScenario.TRAVEL to TenseFormExample(
                    "He was traveling when the storm started.",
                    "Он был в пути, когда начался шторм.",
                    "was traveling",
                ),
                TenseScenario.STUDY to TenseFormExample(
                    "They were studying when the fire alarm rang.",
                    "Они занимались, когда сработала пожарная сигнализация.",
                    "were studying",
                ),
            ),
            usageCases = listOf(
                TenseUsageCase(
                    "Действие в процессе в определённый момент в прошлом",
                    TenseExample("At 8 p.m. I was having dinner.", "В 8 вечера я ужинал(а)."),
                ),
                TenseUsageCase(
                    "Фон для другого, более короткого действия",
                    TenseExample("She was cooking when the phone rang.", "Она готовила, когда зазвонил телефон."),
                ),
                TenseUsageCase(
                    "Два параллельных длительных действия в прошлом",
                    TenseExample(
                        "While I was reading, he was watching TV.",
                        "Пока я читал(а), он смотрел телевизор.",
                    ),
                ),
            ),
            markers = listOf("while", "when", "as", "at that moment", "all evening"),
            markerExamples = listOf(
                TenseExample(
                    "While I was walking home, it started to rain.",
                    "Пока я шёл(шла) домой, начался дождь.",
                ),
                TenseExample("At that moment, everyone was looking at her.", "В тот момент все смотрели на неё."),
            ),
            commonMistake = "Используют Past Continuous для перечисления последовательных завершённых " +
                "действий вместо Past Simple: ✗ I was coming home, was cooking dinner and was watching TV " +
                "вместо ✓ I came home, cooked dinner and watched TV. Continuous — это фон или процесс, а " +
                "не цепочка событий.",
            confusedWith = listOf("past_simple", "past_perfect_continuous"),
            specialNotes = listOf(
                TenseUsageCase(
                    "С always/constantly Past Continuous выражает раздражение по поводу повторяющегося " +
                        "действия в прошлом — почти как жалоба.",
                    TenseExample("He was always losing his keys.", "Он вечно терял ключи (это раздражало)."),
                ),
            ),
        ),
        tense(
            id = "past_perfect",
            time = TenseTime.PAST,
            aspect = TenseAspect.PERFECT,
            titleEn = "Past Perfect",
            titleRu = "Прошедшее совершенное",
            formulas = TenseFormulas(
                affirmative = "S + had + V3",
                negative = "S + had + not + V3",
                question = "Had + S + V3?",
            ),
            scenarios = mapOf(
                TenseScenario.READ_BOOK to TenseFormExample(
                    "She had read a book before the film started.",
                    "Она уже прочитала книгу до того, как начался фильм.",
                    "had read",
                ),
                TenseScenario.CALL_FRIEND to TenseFormExample(
                    "We had called our friend before the meeting started.",
                    "Мы уже позвонили другу до того, как началась встреча.",
                    "had called",
                ),
                TenseScenario.FINISH_PROJECT to TenseFormExample(
                    "They had finished the project before the deadline.",
                    "Они завершили проект до дедлайна.",
                    "had finished",
                ),
                TenseScenario.TRAVEL to TenseFormExample(
                    "He had traveled to Paris before he turned twenty.",
                    "Он уже бывал в Париже до того, как ему исполнилось двадцать.",
                    "had traveled",
                ),
                TenseScenario.STUDY to TenseFormExample(
                    "They had studied English before they moved abroad.",
                    "Они уже изучали английский до того, как переехали за границу.",
                    "had studied",
                ),
            ),
            usageCases = listOf(
                TenseUsageCase(
                    "Действие завершилось до другого действия/момента в прошлом",
                    TenseExample(
                        "By the time we arrived, the film had already started.",
                        "К тому времени, как мы приехали, фильм уже начался.",
                    ),
                ),
                TenseUsageCase(
                    "Причина более раннего события, объясняющая более позднее",
                    TenseExample(
                        "She was upset because she had failed the exam.",
                        "Она расстроилась, потому что провалила экзамен.",
                    ),
                ),
            ),
            markers = listOf("before", "after", "by the time", "already", "just", "when"),
            markerExamples = listOf(
                TenseExample(
                    "She had already left by the time I called.",
                    "Она уже ушла к тому времени, когда я позвонил.",
                ),
                TenseExample(
                    "After they had eaten, they went for a walk.",
                    "После того как они поели, они пошли на прогулку.",
                ),
            ),
            commonMistake = "Забывают использовать Past Perfect для более раннего из двух прошлых событий, " +
                "используя Past Simple для обоих: ✗ When I came, she left (неясно, что было раньше) вместо " +
                "✓ When I came, she had left (она ушла до моего прихода). Порядок слов в предложении не " +
                "гарантирует порядок событий во времени.",
            confusedWith = listOf("past_simple", "past_perfect_continuous"),
            specialNotes = listOf(
                TenseUsageCase(
                    "Если порядок событий и так ясен из after/before, Past Perfect необязателен — можно " +
                        "обойтись Past Simple.",
                    TenseExample(
                        "After she finished the exam, she went home.",
                        "После того как она закончила экзамен, она пошла домой.",
                    ),
                ),
            ),
        ),
        tense(
            id = "past_perfect_continuous",
            time = TenseTime.PAST,
            aspect = TenseAspect.PERFECT_CONTINUOUS,
            titleEn = "Past Perfect Continuous",
            titleRu = "Прошедшее совершенное продолженное",
            formulas = TenseFormulas(
                affirmative = "S + had + been + V-ing",
                negative = "S + had + not + been + V-ing",
                question = "Had + S + been + V-ing?",
            ),
            scenarios = mapOf(
                TenseScenario.READ_BOOK to TenseFormExample(
                    "She had been reading a book for an hour before the film started.",
                    "Она уже час читала книгу, когда начался фильм.",
                    "had been reading",
                ),
                TenseScenario.CALL_FRIEND to TenseFormExample(
                    "We had been calling our friend for ten minutes before the meeting started.",
                    "Мы уже десять минут звонили другу, когда началась встреча.",
                    "had been calling",
                ),
                TenseScenario.FINISH_PROJECT to TenseFormExample(
                    "They had been finishing the project for weeks before the deadline.",
                    "Они уже несколько недель заканчивали проект до дедлайна.",
                    "had been finishing",
                ),
                TenseScenario.TRAVEL to TenseFormExample(
                    "He had been traveling for a week before he got sick.",
                    "Он путешествовал уже неделю, когда заболел.",
                    "had been traveling",
                ),
                TenseScenario.STUDY to TenseFormExample(
                    "They had been studying for three hours before the break.",
                    "Они занимались уже три часа перед перерывом.",
                    "had been studying",
                ),
            ),
            usageCases = listOf(
                TenseUsageCase(
                    "Длительность действия, продолжавшегося до момента в прошлом",
                    TenseExample(
                        "He was tired because he had been working all day.",
                        "Он устал, потому что весь день работал.",
                    ),
                ),
                TenseUsageCase(
                    "Причинно-следственная связь с акцентом на процесс, а не результат",
                    TenseExample(
                        "The ground was wet because it had been raining.",
                        "Земля была мокрой, потому что шёл дождь.",
                    ),
                ),
            ),
            markers = listOf("for", "since", "before", "how long"),
            markerExamples = listOf(
                TenseExample(
                    "He had been studying for three hours before the exam.",
                    "Он готовился три часа перед экзаменом.",
                ),
                TenseExample("They had been waiting since morning.", "Они ждали с самого утра."),
            ),
            commonMistake = "Путают с Past Continuous, не отражая длительность действия ДО момента в " +
                "прошлом: Past Continuous описывает процесс в моменте, а Past Perfect Continuous — сколько " +
                "он уже длился к этому моменту.",
            confusedWith = listOf("past_perfect", "past_continuous"),
            specialNotes = listOf(
                TenseUsageCase(
                    "Как и остальные continuous-формы, не используется с глаголами состояния — вместо неё " +
                        "берётся Past Perfect.",
                    TenseExample("He had known her for years.", "Он знал её много лет."),
                ),
            ),
        ),
        tense(
            id = "future_simple",
            time = TenseTime.FUTURE,
            aspect = TenseAspect.SIMPLE,
            titleEn = "Future Simple",
            titleRu = "Будущее простое",
            formulas = TenseFormulas(
                affirmative = "S + will + V",
                negative = "S + will + not + V",
                question = "Will + S + V?",
            ),
            scenarios = mapOf(
                TenseScenario.READ_BOOK to TenseFormExample(
                    "She will read a book tomorrow.",
                    "Она прочитает книгу завтра.",
                    "will read",
                ),
                TenseScenario.CALL_FRIEND to TenseFormExample(
                    "We will call our friend tomorrow.",
                    "Мы позвоним другу завтра.",
                    "will call",
                ),
                TenseScenario.FINISH_PROJECT to TenseFormExample(
                    "They will finish the project next month.",
                    "Они завершат проект в следующем месяце.",
                    "will finish",
                ),
                TenseScenario.TRAVEL to TenseFormExample(
                    "He will travel to Paris next year.",
                    "Он поедет в Париж в следующем году.",
                    "will travel",
                ),
                TenseScenario.STUDY to TenseFormExample(
                    "They will study English next semester.",
                    "Они будут изучать английский в следующем семестре.",
                    "will study",
                ),
            ),
            usageCases = listOf(
                TenseUsageCase(
                    "Спонтанное решение, принятое прямо в момент речи",
                    TenseExample("I'm thirsty — I will get some water.", "Мне хочется пить — я возьму воды."),
                ),
                TenseUsageCase(
                    "Предсказание без явных доказательств",
                    TenseExample("I think it will rain tomorrow.", "Я думаю, завтра пойдёт дождь."),
                ),
                TenseUsageCase(
                    "Обещание",
                    TenseExample("I will call you tonight, I promise.", "Я позвоню тебе сегодня вечером, обещаю."),
                ),
            ),
            markers = listOf("tomorrow", "next week", "next month", "next year", "soon", "I think", "probably"),
            markerExamples = listOf(
                TenseExample("We will see him tomorrow.", "Мы увидим его завтра."),
                TenseExample("The results will be ready soon.", "Результаты будут готовы скоро."),
            ),
            commonMistake = "Используют will для заранее спланированных решений вместо going to: will — " +
                "это спонтанное решение прямо сейчас (I will do it), а going to — уже принятое заранее " +
                "решение (I'm going to do it). В русском для обоих случаев естественно звучит одна и та " +
                "же форма будущего времени.",
            confusedWith = listOf("future_continuous", "present_continuous"),
            specialNotes = listOf(
                TenseUsageCase(
                    "Will не используется в придаточных времени и условия — вместо него берётся Present " +
                        "Simple (см. также заметку у Present Simple).",
                    TenseExample("I'll text you when I arrive.", "Я напишу тебе, когда приеду."),
                ),
            ),
        ),
        tense(
            id = "future_continuous",
            time = TenseTime.FUTURE,
            aspect = TenseAspect.CONTINUOUS,
            titleEn = "Future Continuous",
            titleRu = "Будущее продолженное",
            formulas = TenseFormulas(
                affirmative = "S + will + be + V-ing",
                negative = "S + will + not + be + V-ing",
                question = "Will + S + be + V-ing?",
            ),
            scenarios = mapOf(
                TenseScenario.READ_BOOK to TenseFormExample(
                    "She will be reading a book at 8 p.m.",
                    "В 8 вечера она будет читать книгу.",
                    "will be reading",
                ),
                TenseScenario.CALL_FRIEND to TenseFormExample(
                    "We will be calling our friend at noon.",
                    "В полдень мы будем звонить другу.",
                    "will be calling",
                ),
                TenseScenario.FINISH_PROJECT to TenseFormExample(
                    "They will be finishing the project at this time next week.",
                    "В это же время на следующей неделе они будут заканчивать проект.",
                    "will be finishing",
                ),
                TenseScenario.TRAVEL to TenseFormExample(
                    "He will be traveling this time next week.",
                    "На следующей неделе в это же время он будет в пути.",
                    "will be traveling",
                ),
                TenseScenario.STUDY to TenseFormExample(
                    "They will be studying at 6 p.m. tomorrow.",
                    "Завтра в 6 вечера они будут заниматься.",
                    "will be studying",
                ),
            ),
            usageCases = listOf(
                TenseUsageCase(
                    "Действие будет в процессе в определённый момент будущего",
                    TenseExample(
                        "This time tomorrow, I will be flying to London.",
                        "Завтра в это же время я буду лететь в Лондон.",
                    ),
                ),
                TenseUsageCase(
                    "Запланированное действие как часть обычного хода событий",
                    TenseExample("Don't call at 9 — I'll be sleeping.", "Не звони в 9 — я буду спать."),
                ),
            ),
            markers = listOf("at this time tomorrow", "this time next week", "at 5 o'clock tomorrow"),
            markerExamples = listOf(
                TenseExample(
                    "At this time tomorrow, we will be sitting on the beach.",
                    "Завтра в это же время мы будем сидеть на пляже.",
                ),
                TenseExample(
                    "This time next week, she will be flying to Rome.",
                    "На следующей неделе в это же время она будет лететь в Рим.",
                ),
            ),
            commonMistake = "Не используют Future Continuous для вежливых вопросов о планах: «Will you be " +
                "using the car tonight?» звучит вежливее и естественнее, чем прямое «Will you use the car?».",
            confusedWith = listOf("future_simple", "future_perfect"),
            specialNotes = listOf(
                TenseUsageCase(
                    "Часто используется для вежливого уточнения планов собеседника вместо прямого вопроса.",
                    TenseExample("Will you be using the printer soon?", "Ты скоро будешь пользоваться принтером?"),
                ),
            ),
        ),
        tense(
            id = "future_perfect",
            time = TenseTime.FUTURE,
            aspect = TenseAspect.PERFECT,
            titleEn = "Future Perfect",
            titleRu = "Будущее совершенное",
            formulas = TenseFormulas(
                affirmative = "S + will + have + V3",
                negative = "S + will + not + have + V3",
                question = "Will + S + have + V3?",
            ),
            scenarios = mapOf(
                TenseScenario.READ_BOOK to TenseFormExample(
                    "She will have read a book by Friday.",
                    "К пятнице она уже прочитает книгу.",
                    "will have read",
                ),
                TenseScenario.CALL_FRIEND to TenseFormExample(
                    "We will have called our friend by 5 p.m.",
                    "К пяти часам мы уже позвоним другу.",
                    "will have called",
                ),
                TenseScenario.FINISH_PROJECT to TenseFormExample(
                    "They will have finished the project by December.",
                    "К декабрю они уже завершат проект.",
                    "will have finished",
                ),
                TenseScenario.TRAVEL to TenseFormExample(
                    "He will have traveled to twenty countries by 2030.",
                    "К 2030 году он побывает в двадцати странах.",
                    "will have traveled",
                ),
                TenseScenario.STUDY to TenseFormExample(
                    "They will have studied all the units by June.",
                    "К июню они изучат все разделы.",
                    "will have studied",
                ),
            ),
            usageCases = listOf(
                TenseUsageCase(
                    "Действие завершится к определённому моменту в будущем",
                    TenseExample(
                        "By next year, she will have graduated.",
                        "К следующему году она уже закончит университет.",
                    ),
                ),
                TenseUsageCase(
                    "Подведение итога к дедлайну",
                    TenseExample(
                        "We will have finished the report by Monday.",
                        "Мы закончим отчёт к понедельнику.",
                    ),
                ),
            ),
            markers = listOf("by", "by the time", "before"),
            markerExamples = listOf(
                TenseExample(
                    "By the end of the year, he will have saved enough money.",
                    "К концу года он уже накопит достаточно денег.",
                ),
                TenseExample(
                    "By the time you arrive, we will have left.",
                    "К тому времени, как ты приедешь, мы уже уедем.",
                ),
            ),
            commonMistake = "Забывают, что действие должно завершиться именно К определённому моменту в " +
                "будущем, и путают с Future Simple, у которого нет такой привязки к дедлайну.",
            confusedWith = listOf("future_perfect_continuous", "future_simple"),
            specialNotes = listOf(
                TenseUsageCase(
                    "By + момент времени указывает на крайний срок, а in + период — просто на то, сколько " +
                        "времени пройдёт, без обязательного дедлайна.",
                    TenseExample("We will have finished by June.", "Мы закончим к июню."),
                ),
            ),
        ),
        tense(
            id = "future_perfect_continuous",
            time = TenseTime.FUTURE,
            aspect = TenseAspect.PERFECT_CONTINUOUS,
            titleEn = "Future Perfect Continuous",
            titleRu = "Будущее совершенное продолженное",
            formulas = TenseFormulas(
                affirmative = "S + will + have + been + V-ing",
                negative = "S + will + not + have + been + V-ing",
                question = "Will + S + have + been + V-ing?",
            ),
            scenarios = mapOf(
                TenseScenario.READ_BOOK to TenseFormExample(
                    "She will have been reading a book for three hours by noon.",
                    "К полудню она будет читать книгу уже три часа.",
                    "will have been reading",
                ),
                TenseScenario.CALL_FRIEND to TenseFormExample(
                    "We will have been calling our friend for an hour by 5 p.m.",
                    "К пяти часам мы будем звонить другу уже час.",
                    "will have been calling",
                ),
                TenseScenario.FINISH_PROJECT to TenseFormExample(
                    "They will have been finishing the project for six months by December.",
                    "К декабрю они будут заканчивать проект уже полгода.",
                    "will have been finishing",
                ),
                TenseScenario.TRAVEL to TenseFormExample(
                    "He will have been traveling for a year by December.",
                    "К декабрю он будет путешествовать уже год.",
                    "will have been traveling",
                ),
                TenseScenario.STUDY to TenseFormExample(
                    "They will have been studying English for ten years by 2030.",
                    "К 2030 году они будут изучать английский уже десять лет.",
                    "will have been studying",
                ),
            ),
            usageCases = listOf(
                TenseUsageCase(
                    "Длительность процесса, который продлится до момента в будущем",
                    TenseExample(
                        "By June, I will have been working here for five years.",
                        "К июню я буду работать здесь уже пять лет.",
                    ),
                ),
                TenseUsageCase(
                    "Акцент на непрерывности процесса перед важной точкой в будущем",
                    TenseExample(
                        "By 10 p.m., they will have been driving for six hours.",
                        "К 10 вечера они будут в пути уже шесть часов.",
                    ),
                ),
            ),
            markers = listOf("by...for", "by the time"),
            markerExamples = listOf(
                TenseExample(
                    "By 2027, I will have been living here for ten years.",
                    "К 2027 году я буду жить здесь уже десять лет.",
                ),
                TenseExample(
                    "By the time she retires, she will have been teaching for thirty years.",
                    "К моменту выхода на пенсию она будет преподавать уже тридцать лет.",
                ),
            ),
            commonMistake = "Самое редкое из времён — его часто просто избегают, заменяя на Future " +
                "Perfect и теряя акцент на длительности самого процесса, а не только на его завершении.",
            confusedWith = listOf("future_perfect", "past_perfect_continuous"),
            specialNotes = listOf(
                TenseUsageCase(
                    "Практически не используется в вопросах и отрицаниях — почти всегда только в " +
                        "утвердительной форме.",
                    TenseExample(
                        "By 10 p.m. I will have been working for twelve hours.",
                        "К 10 вечера я буду работать уже двенадцать часов.",
                    ),
                ),
            ),
        ),
    )

private val COMPARISONS =
    listOf(
        TenseComparison(
            id = "present_simple_vs_present_continuous",
            tenseIdA = "present_simple",
            tenseIdB = "present_continuous",
            tip = "Спросите себя: это происходит вообще/обычно (Simple) — или именно сейчас/временно (Continuous)?",
            pointsA = listOf("Привычки и факты", "Постоянные истины и характеристики", "Расписания"),
            pointsB = listOf(
                "Действие в момент речи",
                "Временная ситуация вокруг «сейчас»",
                "Часто с now, right now",
            ),
            exampleA = TenseExample("I read the news every morning.", "Я читаю новости каждое утро."),
            exampleB = TenseExample("I am reading the news right now.", "Я читаю новости прямо сейчас."),
        ),
        TenseComparison(
            id = "present_perfect_vs_past_simple",
            tenseIdA = "present_perfect",
            tenseIdB = "past_simple",
            tip = "Если есть точное время в прошлом (yesterday, in 2020, last week) — только Past Simple. " +
                "Если время не важно, а важен результат сейчас — Present Perfect.",
            pointsA = listOf(
                "Результат важен сейчас",
                "Время действия не указано",
                "Часто с already, just, yet, ever",
            ),
            pointsB = listOf(
                "Указано конкретное время в прошлом",
                "Нет связи с настоящим",
                "Часто с yesterday, in 2010, ago",
            ),
            exampleA = TenseExample("I have lost my phone.", "Я потерял телефон (сейчас у меня его нет)."),
            exampleB = TenseExample("I lost my phone yesterday.", "Я потерял телефон вчера."),
        ),
        TenseComparison(
            id = "present_perfect_vs_present_perfect_continuous",
            tenseIdA = "present_perfect",
            tenseIdB = "present_perfect_continuous",
            tip = "Perfect — важен результат/факт завершения. Perfect Continuous — важен сам процесс " +
                "и его длительность.",
            pointsA = listOf("Акцент на результате", "Действие обычно завершено", "Можно указать количество"),
            pointsB = listOf(
                "Акцент на процессе и длительности",
                "Действие могло продолжаться до сих пор",
                "Часто с for/since",
            ),
            exampleA = TenseExample("I have written five emails today.", "Я написал(а) пять писем сегодня."),
            exampleB = TenseExample("I have been writing emails all morning.", "Я пишу письма всё утро."),
        ),
        TenseComparison(
            id = "present_continuous_vs_present_perfect_continuous",
            tenseIdA = "present_continuous",
            tenseIdB = "present_perfect_continuous",
            tip = "Continuous — что происходит прямо сейчас. Perfect Continuous — сколько времени действие " +
                "УЖЕ длится к текущему моменту.",
            pointsA = listOf("Действие в моменте речи", "Не важно, когда оно началось"),
            pointsB = listOf("Действие началось раньше и продолжается", "Важна длительность (for/since)"),
            exampleA = TenseExample("It is raining.", "Идёт дождь."),
            exampleB = TenseExample("It has been raining since morning.", "Дождь идёт с самого утра."),
        ),
        TenseComparison(
            id = "past_simple_vs_past_continuous",
            tenseIdA = "past_simple",
            tenseIdB = "past_continuous",
            tip = "Simple — законченное событие («что произошло»). Continuous — фон/процесс, на котором " +
                "происходит другое событие («что происходило, когда...»).",
            pointsA = listOf("Законченное действие в прошлом", "Последовательность событий"),
            pointsB = listOf(
                "Процесс/фон в определённый момент прошлого",
                "Часто прерывается действием в Past Simple",
            ),
            exampleA = TenseExample("The phone rang.", "Зазвонил телефон."),
            exampleB = TenseExample("I was sleeping when the phone rang.", "Я спал(а), когда зазвонил телефон."),
        ),
        TenseComparison(
            id = "past_simple_vs_past_perfect",
            tenseIdA = "past_simple",
            tenseIdB = "past_perfect",
            tip = "Если в предложении два действия в прошлом, более раннее из них — Past Perfect, более " +
                "позднее — Past Simple. Порядок слов не гарантирует порядок событий!",
            pointsA = listOf("Более позднее из двух прошлых событий", "Или единственное событие в прошлом"),
            pointsB = listOf("Более раннее из двух прошлых событий", "Завершилось до другого момента в прошлом"),
            exampleA = TenseExample("I arrived at the station at 5 p.m.", "Я приехал(а) на вокзал в 5 часов."),
            exampleB = TenseExample(
                "The train had already left by the time I arrived.",
                "Поезд уже уехал к моменту, когда я приехал(а).",
            ),
        ),
        TenseComparison(
            id = "past_continuous_vs_past_perfect_continuous",
            tenseIdA = "past_continuous",
            tenseIdB = "past_perfect_continuous",
            tip = "Past Continuous — процесс идёт в конкретный момент прошлого. Past Perfect Continuous — " +
                "сколько по времени процесс уже длился ДО этого момента.",
            pointsA = listOf("Процесс в конкретный момент прошлого"),
            pointsB = listOf("Длительность процесса до другого момента в прошлом", "Часто с for/since"),
            exampleA = TenseExample("At 6 p.m. she was cooking dinner.", "В 6 вечера она готовила ужин."),
            exampleB = TenseExample(
                "She had been cooking dinner for an hour when the guests arrived.",
                "Она готовила ужин уже час, когда пришли гости.",
            ),
        ),
        TenseComparison(
            id = "future_simple_vs_present_continuous",
            tenseIdA = "future_simple",
            tenseIdB = "present_continuous",
            tip = "Will — решение или предположение прямо сейчас. Present Continuous для будущего — уже " +
                "подтверждённый, договорённый план.",
            pointsA = listOf("Спонтанное решение или предположение", "Ещё ничего не решено заранее"),
            pointsB = listOf("Уже согласованный, конкретный план", "Обычно с указанием времени/даты"),
            exampleA = TenseExample(
                "Maybe I will visit her next week.",
                "Может быть, я навещу её на следующей неделе.",
            ),
            exampleB = TenseExample(
                "I am visiting her next Tuesday at 5 p.m.",
                "Я иду к ней в гости в следующий вторник в 5 часов (уже договорились).",
            ),
        ),
        TenseComparison(
            id = "future_simple_vs_future_continuous",
            tenseIdA = "future_simple",
            tenseIdB = "future_continuous",
            tip = "Will — единичное действие или решение. Future Continuous — действие будет В ПРОЦЕССЕ в " +
                "конкретный момент будущего, без волевого решения.",
            pointsA = listOf("Разовое действие или решение"),
            pointsB = listOf("Действие в процессе в определённый момент будущего", "Вежливый вопрос о планах"),
            exampleA = TenseExample("I will call her tomorrow.", "Я позвоню ей завтра."),
            exampleB = TenseExample(
                "I will be calling her at 8 p.m. tomorrow.",
                "Завтра в 8 вечера я буду ей звонить.",
            ),
        ),
        TenseComparison(
            id = "future_perfect_vs_future_perfect_continuous",
            tenseIdA = "future_perfect",
            tenseIdB = "future_perfect_continuous",
            tip = "Future Perfect — действие завершится К определённому моменту. Future Perfect Continuous " +
                "— сколько времени процесс будет ДЛИТЬСЯ к этому моменту.",
            pointsA = listOf("Действие завершено к моменту в будущем", "Акцент на результате"),
            pointsB = listOf("Процесс продолжается вплоть до момента в будущем", "Акцент на длительности (for)"),
            exampleA = TenseExample(
                "By 10 p.m. I will have finished my homework.",
                "К 10 вечера я уже закончу домашнее задание.",
            ),
            exampleB = TenseExample(
                "By 10 p.m. I will have been doing my homework for four hours.",
                "К 10 вечера я буду делать домашнее задание уже четыре часа.",
            ),
        ),
    )

// A content-authoring helper, not a general API — grouping its parameters into wrapper types
// would only add indirection for the one call site pattern below.
@Suppress("LongParameterList")
private fun tense(
    id: String,
    time: TenseTime,
    aspect: TenseAspect,
    titleEn: String,
    titleRu: String,
    formulas: TenseFormulas,
    scenarios: Map<TenseScenario, TenseFormExample>,
    usageCases: List<TenseUsageCase>,
    markers: List<String>,
    markerExamples: List<TenseExample>,
    commonMistake: String,
    confusedWith: List<String>,
    specialNotes: List<TenseUsageCase>,
): Tense =
    Tense(
        id = id,
        time = time,
        aspect = aspect,
        titleEn = titleEn,
        titleRu = titleRu,
        formulas = formulas,
        formExample = scenarios.getValue(TenseScenario.READ_BOOK),
        scenarios = scenarios,
        usageCases = usageCases,
        markers = markers,
        markerExamples = markerExamples,
        commonMistake = commonMistake,
        confusedWith = confusedWith,
        specialNotes = specialNotes,
    )
