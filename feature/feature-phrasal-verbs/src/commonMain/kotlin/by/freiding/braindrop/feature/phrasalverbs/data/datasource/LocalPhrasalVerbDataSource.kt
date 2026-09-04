package by.freiding.braindrop.feature.phrasalverbs.data.datasource

import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerb
import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerbCategory
import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerbExample
import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerbMeaning
import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerbRegister

class LocalPhrasalVerbDataSource {
    fun getVerbs(): List<PhrasalVerb> = VERBS

    fun getById(id: String): PhrasalVerb? = VERBS.firstOrNull { it.id == id }

    private companion object {
        private fun ex(
            en: String,
            ru: String,
        ) = PhrasalVerbExample(en, ru)

        private fun meaning(
            definition: String,
            translation: String,
            vararg examples: PhrasalVerbExample,
            register: PhrasalVerbRegister = PhrasalVerbRegister.NEUTRAL,
        ) = PhrasalVerbMeaning(definition, translation, examples.toList(), register)

        val VERBS = listOf(
            PhrasalVerb(
                id = "give_up",
                verb = "give",
                particle = "up",
                isSeparable = true,
                category = PhrasalVerbCategory.GENERAL,
                meanings = listOf(
                    meaning(
                        "to stop doing or trying something",
                        "сдаться, бросить",
                        ex("She gave up smoking last year.", "В прошлом году она бросила курить."),
                        ex("Don't give up — you're almost there!", "Не сдавайся — ты почти у цели!"),
                    ),
                    meaning(
                        "to hand something over to someone",
                        "отдать, уступить",
                        ex("He gave up his seat on the bus.", "Он уступил место в автобусе."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "break_down",
                verb = "break",
                particle = "down",
                isSeparable = false,
                category = PhrasalVerbCategory.CHANGES,
                meanings = listOf(
                    meaning(
                        "to stop working (for a machine or vehicle)",
                        "сломаться, выйти из строя",
                        ex("The car broke down on the motorway.", "Машина сломалась на шоссе."),
                        ex("The heating system broke down in winter.", "Система отопления вышла из строя зимой."),
                    ),
                    meaning(
                        "to lose control of emotions and start crying",
                        "расплакаться, не выдержать",
                        ex("She broke down when she heard the news.", "Она расплакалась, узнав новость."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "set_up",
                verb = "set",
                particle = "up",
                isSeparable = true,
                category = PhrasalVerbCategory.WORK,
                meanings = listOf(
                    meaning(
                        "to start or establish a business or organisation",
                        "основать, организовать",
                        ex("They set up a new company last spring.", "Они основали новую компанию прошлой весной."),
                        ex("She set up her own design studio.", "Она открыла собственную дизайн-студию."),
                    ),
                    meaning(
                        "to arrange or prepare equipment for use",
                        "настроить, подготовить",
                        ex(
                            "Can you set up the projector for the meeting?",
                            "Ты можешь настроить проектор для встречи?",
                        ),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "find_out",
                verb = "find",
                particle = "out",
                isSeparable = true,
                category = PhrasalVerbCategory.COMMUNICATION,
                meanings = listOf(
                    meaning(
                        "to discover or learn information",
                        "узнать, выяснить",
                        ex("She found out the truth about him.", "Она узнала правду о нём."),
                        ex("Did you find out when the train leaves?", "Ты узнал, когда отходит поезд?"),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "take_on",
                verb = "take",
                particle = "on",
                isSeparable = true,
                category = PhrasalVerbCategory.WORK,
                meanings = listOf(
                    meaning(
                        "to accept responsibility or a task",
                        "взять на себя, принять",
                        ex(
                            "She took on too much work this month.",
                            "В этом месяце она взяла на себя слишком много работы.",
                        ),
                        ex(
                            "The company took on five new employees.",
                            "Компания взяла на работу пятерых новых сотрудников.",
                        ),
                    ),
                    meaning(
                        "to compete against someone",
                        "сразиться, выступить против",
                        ex(
                            "Our team took on the reigning champions.",
                            "Наша команда сразилась с действующими чемпионами.",
                        ),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "turn_down",
                verb = "turn",
                particle = "down",
                isSeparable = true,
                category = PhrasalVerbCategory.WORK,
                meanings = listOf(
                    meaning(
                        "to reject or refuse an offer or request",
                        "отклонить, отказать",
                        ex("He turned down the job offer.", "Он отклонил предложение о работе."),
                        ex(
                            "She turned down the invitation to the party.",
                            "Она отказалась от приглашения на вечеринку.",
                        ),
                    ),
                    meaning(
                        "to reduce the volume, heat, or level of something",
                        "убавить, уменьшить",
                        ex("Please turn down the music.", "Пожалуйста, убавь музыку."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "put_off",
                verb = "put",
                particle = "off",
                isSeparable = true,
                category = PhrasalVerbCategory.WORK,
                meanings = listOf(
                    meaning(
                        "to delay or postpone something until a later time",
                        "откладывать, переносить",
                        ex("Stop putting off the difficult tasks.", "Перестань откладывать трудные задачи."),
                        ex("The match was put off due to rain.", "Матч перенесли из-за дождя."),
                    ),
                    meaning(
                        "to cause someone to lose interest or enthusiasm",
                        "отпугнуть, отбить интерес",
                        ex(
                            "The high prices put her off buying a new phone.",
                            "Высокие цены отбили у неё желание покупать новый телефон.",
                        ),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "carry_out",
                verb = "carry",
                particle = "out",
                isSeparable = true,
                category = PhrasalVerbCategory.WORK,
                meanings = listOf(
                    meaning(
                        "to complete a task or plan",
                        "выполнять, осуществлять",
                        ex("They carried out a series of experiments.", "Они провели серию экспериментов."),
                        ex("The survey was carried out in three cities.", "Опрос проводился в трёх городах."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "come_up_with",
                verb = "come",
                particle = "up with",
                isSeparable = false,
                category = PhrasalVerbCategory.COMMUNICATION,
                meanings = listOf(
                    meaning(
                        "to think of an idea or solution",
                        "придумать, предложить",
                        ex("She came up with a brilliant solution.", "Она придумала блестящее решение."),
                        ex("Can you come up with a better plan?", "Ты можешь предложить план получше?"),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "get_on",
                verb = "get",
                particle = "on",
                isSeparable = false,
                category = PhrasalVerbCategory.RELATIONSHIPS,
                meanings = listOf(
                    meaning(
                        "to have a friendly relationship with someone",
                        "ладить, хорошо общаться",
                        ex("Do you get on well with your colleagues?", "Ты хорошо ладишь со своими коллегами?"),
                        ex("She gets on with everyone in the office.", "Она ладит со всеми в офисе."),
                    ),
                    meaning(
                        "to make progress or continue with something",
                        "продвигаться, продолжать",
                        ex("Let's get on with the meeting.", "Давайте продолжим встречу."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "fall_out",
                verb = "fall",
                particle = "out",
                isSeparable = false,
                category = PhrasalVerbCategory.RELATIONSHIPS,
                meanings = listOf(
                    meaning(
                        "to have a quarrel or argument and stop being friends",
                        "поссориться, рассориться",
                        ex("They fell out over a misunderstanding.", "Они поссорились из-за недоразумения."),
                        ex(
                            "She fell out with her best friend last year.",
                            "В прошлом году она поссорилась с лучшей подругой.",
                        ),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "make_up",
                verb = "make",
                particle = "up",
                isSeparable = true,
                category = PhrasalVerbCategory.RELATIONSHIPS,
                meanings = listOf(
                    meaning(
                        "to become friends again after an argument",
                        "помириться, помириться",
                        ex("They argued but made up the next day.", "Они поспорили, но на следующий день помирились."),
                    ),
                    meaning(
                        "to invent a story or excuse",
                        "придумать, выдумать",
                        ex("He made up an excuse for being late.", "Он придумал отговорку, почему опоздал."),
                        ex("She made up a bedtime story for her kids.", "Она придумала сказку на ночь для детей."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "bring_up",
                verb = "bring",
                particle = "up",
                isSeparable = true,
                category = PhrasalVerbCategory.COMMUNICATION,
                meanings = listOf(
                    meaning(
                        "to introduce a topic into a conversation",
                        "поднять тему, упомянуть",
                        ex(
                            "She brought up the salary issue at the meeting.",
                            "Она подняла вопрос о зарплате на встрече.",
                        ),
                        ex("He didn't want to bring up the argument again.", "Он не хотел снова поднимать этот спор."),
                    ),
                    meaning(
                        "to raise a child",
                        "воспитывать, растить",
                        ex("She was brought up in a small village.", "Она выросла в маленькой деревне."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "grow_up",
                verb = "grow",
                particle = "up",
                isSeparable = false,
                category = PhrasalVerbCategory.CHANGES,
                meanings = listOf(
                    meaning(
                        "to develop from a child into an adult",
                        "вырасти, повзрослеть",
                        ex("Where did you grow up?", "Где ты вырос?"),
                        ex("He wants to be a pilot when he grows up.", "Он хочет стать лётчиком, когда вырастет."),
                    ),
                    meaning(
                        "to become more mature and responsible",
                        "повзрослеть, стать серьёзнее",
                        ex(
                            "It's time to grow up and take responsibility.",
                            "Пора повзрослеть и взять на себя ответственность.",
                        ),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "turn_into",
                verb = "turn",
                particle = "into",
                isSeparable = false,
                category = PhrasalVerbCategory.CHANGES,
                meanings = listOf(
                    meaning(
                        "to change from one state or thing to another",
                        "превратиться, стать",
                        ex("The tadpole turned into a frog.", "Головастик превратился в лягушку."),
                        ex("The argument turned into a big fight.", "Спор перерос в большую ссору."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "get_up",
                verb = "get",
                particle = "up",
                isSeparable = false,
                category = PhrasalVerbCategory.MOVEMENT,
                meanings = listOf(
                    meaning(
                        "to rise from bed or a sitting/lying position",
                        "вставать, подниматься",
                        ex("She gets up at seven every morning.", "Она встаёт в семь каждое утро."),
                        ex("He got up from his chair to greet her.", "Он встал со стула, чтобы поприветствовать её."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "go_out",
                verb = "go",
                particle = "out",
                isSeparable = false,
                category = PhrasalVerbCategory.MOVEMENT,
                meanings = listOf(
                    meaning(
                        "to leave the house for leisure activities",
                        "выходить, идти куда-нибудь",
                        ex("They go out every Friday night.", "Они выходят куда-нибудь каждую пятницу вечером."),
                        ex("Are you going out tonight?", "Ты куда-нибудь выходишь сегодня?"),
                    ),
                    meaning(
                        "to be in a romantic relationship with someone",
                        "встречаться, ходить на свидания",
                        ex("They have been going out for two years.", "Они встречаются уже два года."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "speak_up",
                verb = "speak",
                particle = "up",
                isSeparable = false,
                category = PhrasalVerbCategory.COMMUNICATION,
                meanings = listOf(
                    meaning(
                        "to talk more loudly or express one's opinion",
                        "говорить громче, высказаться",
                        ex("Could you speak up? I can't hear you.", "Не могли бы вы говорить громче? Я вас не слышу."),
                        ex("Don't be afraid to speak up in meetings.", "Не бойся высказываться на встречах."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "point_out",
                verb = "point",
                particle = "out",
                isSeparable = true,
                category = PhrasalVerbCategory.COMMUNICATION,
                meanings = listOf(
                    meaning(
                        "to draw attention to a fact or mistake",
                        "указать, обратить внимание",
                        ex("She pointed out an error in the report.", "Она указала на ошибку в отчёте."),
                        ex(
                            "He kindly pointed out that I had food on my shirt.",
                            "Он любезно указал, что у меня еда на рубашке.",
                        ),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "break_up",
                verb = "break",
                particle = "up",
                isSeparable = false,
                category = PhrasalVerbCategory.RELATIONSHIPS,
                meanings = listOf(
                    meaning(
                        "to end a romantic relationship",
                        "расстаться, разойтись",
                        ex("They broke up after three years together.", "Они расстались после трёх лет вместе."),
                        ex(
                            "She was heartbroken when he broke up with her.",
                            "Она была убита горем, когда он с ней расстался.",
                        ),
                    ),
                    meaning(
                        "to separate or disperse a group",
                        "разогнать, расходиться",
                        ex("The police broke up the crowd.", "Полиция разогнала толпу."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "look_after",
                verb = "look",
                particle = "after",
                isSeparable = false,
                category = PhrasalVerbCategory.GENERAL,
                meanings = listOf(
                    meaning(
                        "to take care of someone or something",
                        "заботиться, присматривать",
                        ex(
                            "Can you look after my dog this weekend?",
                            "Ты можешь присмотреть за моей собакой на выходных?",
                        ),
                        ex("She looks after her elderly parents.", "Она ухаживает за пожилыми родителями."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "run_out_of",
                verb = "run",
                particle = "out of",
                isSeparable = false,
                category = PhrasalVerbCategory.GENERAL,
                meanings = listOf(
                    meaning(
                        "to have no more of something",
                        "закончиться, израсходоваться",
                        ex("We ran out of coffee this morning.", "Сегодня утром у нас закончился кофе."),
                        ex("The printer has run out of ink.", "В принтере закончились чернила."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "keep_up_with",
                verb = "keep",
                particle = "up with",
                isSeparable = false,
                category = PhrasalVerbCategory.GENERAL,
                meanings = listOf(
                    meaning(
                        "to move or progress at the same rate as someone or something",
                        "идти в ногу, не отставать",
                        ex(
                            "It's hard to keep up with all the new technology.",
                            "Трудно идти в ногу с новыми технологиями.",
                        ),
                        ex("She struggled to keep up with the class.", "Ей было трудно успевать за классом."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "look_forward_to",
                verb = "look",
                particle = "forward to",
                isSeparable = false,
                category = PhrasalVerbCategory.GENERAL,
                meanings = listOf(
                    meaning(
                        "to be excited or pleased about something that will happen",
                        "с нетерпением ждать",
                        ex("I'm looking forward to the holidays.", "Я с нетерпением жду праздников."),
                        ex("She looks forward to seeing her family.", "Она с нетерпением ждёт встречи с семьёй."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "hand_in",
                verb = "hand",
                particle = "in",
                isSeparable = true,
                category = PhrasalVerbCategory.WORK,
                meanings = listOf(
                    meaning(
                        "to submit work, a document, or homework to someone in authority",
                        "сдать, вручить",
                        ex("He handed in his resignation this morning.", "Он подал заявление об уходе этим утром."),
                        ex("Please hand in your essays by Friday.", "Пожалуйста, сдайте эссе до пятницы."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "take_over",
                verb = "take",
                particle = "over",
                isSeparable = true,
                category = PhrasalVerbCategory.WORK,
                meanings = listOf(
                    meaning(
                        "to gain control of a company or take responsibility from someone",
                        "захватить, взять под контроль",
                        ex(
                            "The company was taken over by a larger rival.",
                            "Компанию поглотил более крупный конкурент.",
                        ),
                        ex(
                            "She took over as manager when he left.",
                            "Она взяла на себя роль менеджера, когда он ушёл.",
                        ),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "fill_in",
                verb = "fill",
                particle = "in",
                isSeparable = true,
                category = PhrasalVerbCategory.WORK,
                meanings = listOf(
                    meaning(
                        "to complete a form or document",
                        "заполнить",
                        ex("Please fill in this application form.", "Пожалуйста, заполните эту форму заявки."),
                        ex("Fill in your name and address at the top.", "Укажите своё имя и адрес вверху."),
                    ),
                    meaning(
                        "to temporarily replace someone at work",
                        "заменить, подменить",
                        ex("Can you fill in for me tomorrow?", "Ты можешь заменить меня завтра?"),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "call_off",
                verb = "call",
                particle = "off",
                isSeparable = true,
                category = PhrasalVerbCategory.WORK,
                meanings = listOf(
                    meaning(
                        "to cancel a planned event or activity",
                        "отменить",
                        ex(
                            "They called off the meeting at the last minute.",
                            "Они отменили встречу в последнюю минуту.",
                        ),
                        ex("The match was called off due to bad weather.", "Матч отменили из-за плохой погоды."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "ask_out",
                verb = "ask",
                particle = "out",
                isSeparable = true,
                category = PhrasalVerbCategory.RELATIONSHIPS,
                meanings = listOf(
                    meaning(
                        "to invite someone on a romantic date",
                        "пригласить на свидание",
                        ex("He finally asked her out.", "Он наконец-то пригласил её на свидание."),
                        ex("Are you going to ask him out?", "Ты собираешься пригласить его на свидание?"),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "settle_down",
                verb = "settle",
                particle = "down",
                isSeparable = false,
                category = PhrasalVerbCategory.RELATIONSHIPS,
                meanings = listOf(
                    meaning(
                        "to start living a stable, conventional life in one place",
                        "остепениться, осесть",
                        ex(
                            "He settled down after years of travelling.",
                            "После многих лет путешествий он остепенился.",
                        ),
                        ex("She wants to settle down and start a family.", "Она хочет остепениться и создать семью."),
                    ),
                    meaning(
                        "to become calmer or quieter",
                        "успокоиться",
                        ex("Settle down and listen, please.", "Успокойтесь и слушайте, пожалуйста."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "look_up_to",
                verb = "look",
                particle = "up to",
                isSeparable = false,
                category = PhrasalVerbCategory.RELATIONSHIPS,
                meanings = listOf(
                    meaning(
                        "to admire and respect someone",
                        "восхищаться, уважать",
                        ex(
                            "She has always looked up to her older sister.",
                            "Она всегда восхищалась своей старшей сестрой.",
                        ),
                        ex(
                            "Kids look up to sports stars as role models.",
                            "Дети видят в звёздах спорта образец для подражания.",
                        ),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "set_off",
                verb = "set",
                particle = "off",
                isSeparable = false,
                category = PhrasalVerbCategory.MOVEMENT,
                meanings = listOf(
                    meaning(
                        "to start a journey",
                        "отправиться, тронуться в путь",
                        ex("They set off early to avoid the traffic.", "Они отправились рано, чтобы избежать пробок."),
                        ex("We set off at dawn and arrived by noon.", "Мы тронулись на рассвете и прибыли к полудню."),
                    ),
                    meaning(
                        "to cause something to start, explode, or sound",
                        "вызвать, запустить, привести в действие",
                        ex("Someone set off the fire alarm.", "Кто-то привёл в действие пожарную сигнализацию."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "pick_up",
                verb = "pick",
                particle = "up",
                isSeparable = true,
                category = PhrasalVerbCategory.MOVEMENT,
                meanings = listOf(
                    meaning(
                        "to collect someone in a vehicle",
                        "забрать, подвезти",
                        ex("Can you pick me up from the airport?", "Можешь забрать меня из аэропорта?"),
                        ex("She picks up the kids from school every day.", "Она забирает детей из школы каждый день."),
                    ),
                    meaning(
                        "to learn something quickly and informally",
                        "подхватить, усвоить",
                        ex(
                            "She picked up Spanish in just a few months.",
                            "Она освоила испанский всего за несколько месяцев.",
                        ),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "drop_off",
                verb = "drop",
                particle = "off",
                isSeparable = true,
                category = PhrasalVerbCategory.MOVEMENT,
                meanings = listOf(
                    meaning(
                        "to take someone to a place and leave them there",
                        "подбросить, довезти",
                        ex("He dropped the kids off at school.", "Он довёз детей до школы."),
                        ex("Can you drop me off at the station?", "Ты можешь довезти меня до вокзала?"),
                    ),
                    meaning(
                        "to decrease gradually",
                        "уменьшиться, снизиться",
                        ex("Sales dropped off sharply in December.", "В декабре продажи резко упали."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "talk_into",
                verb = "talk",
                particle = "into",
                isSeparable = true,
                category = PhrasalVerbCategory.COMMUNICATION,
                meanings = listOf(
                    meaning(
                        "to persuade someone to do something",
                        "убедить, уговорить",
                        ex("She talked him into going to the gym.", "Она уговорила его пойти в спортзал."),
                        ex(
                            "Don't let them talk you into something you don't want.",
                            "Не позволяй им уговорить тебя на то, чего ты не хочешь.",
                        ),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "put_forward",
                verb = "put",
                particle = "forward",
                isSeparable = true,
                category = PhrasalVerbCategory.COMMUNICATION,
                meanings = listOf(
                    meaning(
                        "to suggest an idea, plan, or candidate",
                        "предложить, выдвинуть",
                        ex(
                            "She put forward a new strategy at the meeting.",
                            "Она предложила новую стратегию на встрече.",
                        ),
                        ex("His name was put forward for the promotion.", "Его имя выдвинули на повышение."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "hand_out",
                verb = "hand",
                particle = "out",
                isSeparable = true,
                category = PhrasalVerbCategory.COMMUNICATION,
                meanings = listOf(
                    meaning(
                        "to distribute something to a group of people",
                        "раздать, раздавать",
                        ex("The teacher handed out worksheets to the class.", "Учитель раздал рабочие листы классу."),
                        ex(
                            "Volunteers were handing out leaflets in the street.",
                            "Волонтёры раздавали листовки на улице.",
                        ),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "die_out",
                verb = "die",
                particle = "out",
                isSeparable = false,
                category = PhrasalVerbCategory.CHANGES,
                meanings = listOf(
                    meaning(
                        "to become extinct or gradually disappear",
                        "вымирать, исчезать",
                        ex(
                            "Many species are dying out due to habitat loss.",
                            "Многие виды вымирают из-за уничтожения среды обитания.",
                        ),
                        ex("This old tradition is slowly dying out.", "Эта старая традиция медленно исчезает."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "wear_off",
                verb = "wear",
                particle = "off",
                isSeparable = false,
                category = PhrasalVerbCategory.CHANGES,
                meanings = listOf(
                    meaning(
                        "to gradually disappear or lose effect",
                        "проходить, улетучиваться",
                        ex("The effect of the painkiller is wearing off.", "Действие обезболивающего проходит."),
                        ex(
                            "The excitement of the new job wore off quickly.",
                            "Воодушевление от новой работы быстро прошло.",
                        ),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "show_up",
                verb = "show",
                particle = "up",
                isSeparable = false,
                category = PhrasalVerbCategory.GENERAL,
                meanings = listOf(
                    meaning(
                        "to arrive or appear somewhere, especially unexpectedly",
                        "появиться, прийти",
                        ex("He showed up an hour late.", "Он появился на час позже."),
                        ex("I was surprised she showed up at all.", "Я удивился, что она вообще пришла."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "figure_out",
                verb = "figure",
                particle = "out",
                isSeparable = true,
                category = PhrasalVerbCategory.GENERAL,
                meanings = listOf(
                    meaning(
                        "to find the solution to something or understand something",
                        "разобраться, понять",
                        ex(
                            "I can't figure out how to use this app.",
                            "Я не могу разобраться, как пользоваться этим приложением.",
                        ),
                        ex("She figured out the answer to the problem.", "Она нашла ответ на задачу."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "put_up_with",
                verb = "put",
                particle = "up with",
                isSeparable = false,
                category = PhrasalVerbCategory.GENERAL,
                meanings = listOf(
                    meaning(
                        "to tolerate something unpleasant without complaining",
                        "терпеть, мириться с",
                        ex("I can't put up with his behaviour any longer.", "Я больше не могу терпеть его поведение."),
                        ex("She puts up with a lot at work.", "Она многое терпит на работе."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "come_across",
                verb = "come",
                particle = "across",
                isSeparable = false,
                category = PhrasalVerbCategory.GENERAL,
                meanings = listOf(
                    meaning(
                        "to find or meet something or someone by chance",
                        "наткнуться, случайно встретить",
                        ex("I came across an old photo in the attic.", "Я наткнулся на старую фотографию на чердаке."),
                        ex(
                            "She came across her school friend in a coffee shop.",
                            "Она случайно встретила школьную подругу в кофейне.",
                        ),
                    ),
                    meaning(
                        "to give a particular impression to others",
                        "производить впечатление",
                        ex(
                            "She comes across as very confident.",
                            "Она производит впечатление очень уверенного человека.",
                        ),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "go_through",
                verb = "go",
                particle = "through",
                isSeparable = false,
                category = PhrasalVerbCategory.GENERAL,
                meanings = listOf(
                    meaning(
                        "to experience a difficult period or situation",
                        "переживать, пройти через",
                        ex("She's going through a tough time right now.", "Сейчас она переживает тяжёлые времена."),
                        ex("He went through a lot after losing his job.", "После потери работы он пережил многое."),
                    ),
                    meaning(
                        "to examine or check something carefully",
                        "просмотреть, проверить",
                        ex("Let's go through the report together.", "Давайте вместе просмотрим отчёт."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "deal_with",
                verb = "deal",
                particle = "with",
                isSeparable = false,
                category = PhrasalVerbCategory.GENERAL,
                meanings = listOf(
                    meaning(
                        "to take action in order to handle a problem or situation",
                        "справляться, разбираться",
                        ex("Can you deal with this complaint?", "Ты можешь разобраться с этой жалобой?"),
                        ex(
                            "He deals with difficult customers every day.",
                            "Он каждый день имеет дело со сложными клиентами.",
                        ),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "catch_up",
                verb = "catch",
                particle = "up",
                isSeparable = false,
                category = PhrasalVerbCategory.GENERAL,
                meanings = listOf(
                    meaning(
                        "to reach someone who is ahead, or to reach the same level as others",
                        "догнать, наверстать",
                        ex("He ran fast to catch up with the others.", "Он бежал быстро, чтобы догнать остальных."),
                        ex("I need to catch up on my homework.", "Мне нужно наверстать домашнее задание."),
                    ),
                    meaning(
                        "to talk to someone and share news after a period of not seeing them",
                        "пообщаться, наверстать упущенное",
                        ex("Let's catch up over coffee sometime.", "Давай как-нибудь поговорим за кофе."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "turn_up",
                verb = "turn",
                particle = "up",
                isSeparable = true,
                category = PhrasalVerbCategory.GENERAL,
                meanings = listOf(
                    meaning(
                        "to arrive or appear, often unexpectedly",
                        "появиться, объявиться",
                        ex(
                            "He turned up at the party without an invitation.",
                            "Он появился на вечеринке без приглашения.",
                        ),
                        ex("The missing keys turned up under the sofa.", "Пропавшие ключи нашлись под диваном."),
                    ),
                    meaning(
                        "to increase the volume or intensity of something",
                        "прибавить, увеличить",
                        ex("Could you turn up the heating?", "Не могли бы вы прибавить отопление?"),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "work_out",
                verb = "work",
                particle = "out",
                isSeparable = true,
                category = PhrasalVerbCategory.GENERAL,
                meanings = listOf(
                    meaning(
                        "to find the answer to something or devise a plan",
                        "найти решение, разобраться",
                        ex("We need to work out a better plan.", "Нам нужно найти лучший план."),
                        ex("I can't work out why she's so upset.", "Я не могу понять, почему она так расстроена."),
                    ),
                    meaning(
                        "to do physical exercise",
                        "тренироваться, заниматься спортом",
                        ex("She works out at the gym three times a week.", "Она тренируется в зале три раза в неделю."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "draw_up",
                verb = "draw",
                particle = "up",
                isSeparable = true,
                category = PhrasalVerbCategory.WORK,
                meanings = listOf(
                    meaning(
                        "to prepare a formal document, plan, or list",
                        "составить, разработать",
                        ex(
                            "The lawyer drew up a contract for both parties.",
                            "Адвокат составил договор для обеих сторон.",
                        ),
                        ex("We need to draw up a project timeline.", "Нам нужно составить график проекта."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "lay_off",
                verb = "lay",
                particle = "off",
                isSeparable = true,
                category = PhrasalVerbCategory.WORK,
                meanings = listOf(
                    meaning(
                        "to dismiss employees because of lack of work or funding",
                        "сократить, уволить",
                        ex("The factory laid off two hundred workers.", "Завод сократил двести рабочих."),
                        ex(
                            "She was laid off during the company restructuring.",
                            "Её уволили в ходе реструктуризации компании.",
                        ),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "sign_up",
                verb = "sign",
                particle = "up",
                isSeparable = false,
                category = PhrasalVerbCategory.WORK,
                meanings = listOf(
                    meaning(
                        "to register or enrol for something",
                        "записаться, зарегистрироваться",
                        ex("She signed up for an online course.", "Она записалась на онлайн-курс."),
                        ex(
                            "You can sign up for the newsletter on the website.",
                            "Вы можете подписаться на рассылку на сайте.",
                        ),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "step_down",
                verb = "step",
                particle = "down",
                isSeparable = false,
                category = PhrasalVerbCategory.WORK,
                meanings = listOf(
                    meaning(
                        "to resign from an important position",
                        "уйти в отставку, покинуть пост",
                        ex(
                            "The CEO stepped down after the scandal.",
                            "Генеральный директор ушёл в отставку после скандала.",
                        ),
                        ex(
                            "She stepped down as chairwoman last month.",
                            "В прошлом месяце она покинула пост председателя.",
                        ),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "open_up",
                verb = "open",
                particle = "up",
                isSeparable = false,
                category = PhrasalVerbCategory.RELATIONSHIPS,
                meanings = listOf(
                    meaning(
                        "to share feelings or thoughts honestly with someone",
                        "открыться, раскрыться",
                        ex(
                            "It took him a long time to open up about his fears.",
                            "Ему потребовалось много времени, чтобы открыться о своих страхах.",
                        ),
                        ex("She finds it hard to open up to new people.", "Ей трудно открываться перед новыми людьми."),
                    ),
                    meaning(
                        "to start a new business or begin trading",
                        "открыться, начать работу",
                        ex("A new bakery opened up on our street.", "На нашей улице открылась новая пекарня."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "patch_up",
                verb = "patch",
                particle = "up",
                isSeparable = true,
                category = PhrasalVerbCategory.RELATIONSHIPS,
                meanings = listOf(
                    meaning(
                        "to repair or resolve a quarrel or damaged relationship",
                        "помириться, уладить",
                        ex(
                            "They patched up their differences and became friends again.",
                            "Они уладили разногласия и снова стали друзьями.",
                        ),
                        ex("Can we patch things up?", "Можем ли мы помириться?"),
                    ),
                    meaning(
                        "to treat injuries in a basic way",
                        "перевязать, оказать первую помощь",
                        ex("The nurse patched up his wound.", "Медсестра перевязала его рану."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "move_in",
                verb = "move",
                particle = "in",
                isSeparable = false,
                category = PhrasalVerbCategory.MOVEMENT,
                meanings = listOf(
                    meaning(
                        "to begin living in a new home",
                        "въехать, переехать",
                        ex(
                            "They moved in to their new flat last weekend.",
                            "На прошлых выходных они въехали в новую квартиру.",
                        ),
                        ex("When are you moving in?", "Когда ты переезжаешь?"),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "move_out",
                verb = "move",
                particle = "out",
                isSeparable = false,
                category = PhrasalVerbCategory.MOVEMENT,
                meanings = listOf(
                    meaning(
                        "to stop living in a place and leave",
                        "выехать, съехать",
                        ex("She moved out after the argument.", "Она съехала после ссоры."),
                        ex("He moved out of his parents' house at 22.", "Он съехал от родителей в 22 года."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "get_away",
                verb = "get",
                particle = "away",
                isSeparable = false,
                category = PhrasalVerbCategory.MOVEMENT,
                meanings = listOf(
                    meaning(
                        "to escape or leave a place, often for a holiday or rest",
                        "сбежать, вырваться",
                        ex("We need to get away for a few days.", "Нам нужно куда-нибудь вырваться на несколько дней."),
                        ex("The thief managed to get away.", "Вору удалось сбежать."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "spell_out",
                verb = "spell",
                particle = "out",
                isSeparable = true,
                category = PhrasalVerbCategory.COMMUNICATION,
                meanings = listOf(
                    meaning(
                        "to explain something very clearly and in detail",
                        "разъяснить, растолковать",
                        ex("Do I have to spell it out for you?", "Мне нужно разжевать тебе это?"),
                        ex(
                            "The instructions don't spell out what to do next.",
                            "В инструкции не объясняется, что делать дальше.",
                        ),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "sum_up",
                verb = "sum",
                particle = "up",
                isSeparable = true,
                category = PhrasalVerbCategory.COMMUNICATION,
                meanings = listOf(
                    meaning(
                        "to briefly state the main points of something",
                        "подытожить, резюмировать",
                        ex(
                            "Could you sum up the main points of the report?",
                            "Не могли бы вы подытожить основные моменты отчёта?",
                        ),
                        ex(
                            "To sum up, we need more time and resources.",
                            "Подводя итог, нам нужно больше времени и ресурсов.",
                        ),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "get_across",
                verb = "get",
                particle = "across",
                isSeparable = true,
                category = PhrasalVerbCategory.COMMUNICATION,
                meanings = listOf(
                    meaning(
                        "to communicate an idea or message successfully",
                        "донести мысль, объяснить",
                        ex(
                            "It's hard to get this concept across to beginners.",
                            "Трудно донести эту концепцию до начинающих.",
                        ),
                        ex("Did the speaker get her point across?", "Удалось ли оратору донести свою мысль?"),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "pass_on",
                verb = "pass",
                particle = "on",
                isSeparable = true,
                category = PhrasalVerbCategory.COMMUNICATION,
                meanings = listOf(
                    meaning(
                        "to give or transmit information, a message, or an item to someone else",
                        "передать, сообщить",
                        ex("Could you pass on the message to her?", "Не могли бы вы передать ей сообщение?"),
                        ex(
                            "He passed on the good news to the rest of the team.",
                            "Он передал хорошие новости остальным членам команды.",
                        ),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "take_off",
                verb = "take",
                particle = "off",
                isSeparable = false,
                category = PhrasalVerbCategory.CHANGES,
                meanings = listOf(
                    meaning(
                        "to suddenly become successful or start to increase rapidly",
                        "взлететь, стремительно расти",
                        ex(
                            "Her career really took off after that film.",
                            "Её карьера действительно пошла в гору после того фильма.",
                        ),
                        ex("Sales took off in the second quarter.", "Продажи резко возросли во втором квартале."),
                    ),
                    meaning(
                        "to leave the ground and fly (for an aircraft)",
                        "взлетать",
                        ex("The plane took off an hour late.", "Самолёт взлетел с опозданием на час."),
                    ),
                    meaning(
                        "to remove something, especially clothing",
                        "снять",
                        ex("Take off your shoes at the door.", "Снимите обувь у двери."),
                        register = PhrasalVerbRegister.NEUTRAL,
                    ),
                ),
            ),
            PhrasalVerb(
                id = "calm_down",
                verb = "calm",
                particle = "down",
                isSeparable = true,
                category = PhrasalVerbCategory.CHANGES,
                meanings = listOf(
                    meaning(
                        "to become less upset, excited, or angry",
                        "успокоиться, угомониться",
                        ex("Take a deep breath and calm down.", "Сделай глубокий вдох и успокойся."),
                        ex("She calmed down after talking to a friend.", "Она успокоилась после разговора с подругой."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "build_up",
                verb = "build",
                particle = "up",
                isSeparable = true,
                category = PhrasalVerbCategory.CHANGES,
                meanings = listOf(
                    meaning(
                        "to increase or accumulate gradually over time",
                        "нарастать, накапливаться",
                        ex("Tension built up throughout the day.", "Напряжение нарастало в течение дня."),
                        ex(
                            "He has built up a lot of experience over the years.",
                            "За эти годы он накопил большой опыт.",
                        ),
                    ),
                    meaning(
                        "to strengthen or develop something",
                        "развивать, укреплять",
                        ex(
                            "She built up her confidence through public speaking.",
                            "Она укрепила уверенность в себе через публичные выступления.",
                        ),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "look_into",
                verb = "look",
                particle = "into",
                isSeparable = false,
                category = PhrasalVerbCategory.GENERAL,
                meanings = listOf(
                    meaning(
                        "to investigate or examine something carefully",
                        "изучить, расследовать",
                        ex("The police are looking into the matter.", "Полиция расследует это дело."),
                        ex("We'll look into the issue and get back to you.", "Мы изучим вопрос и свяжемся с вами."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "get_away_with",
                verb = "get",
                particle = "away with",
                isSeparable = false,
                category = PhrasalVerbCategory.GENERAL,
                meanings = listOf(
                    meaning(
                        "to do something wrong or risky without being caught or punished",
                        "сойти с рук",
                        ex("He got away with cheating on the test.", "Ему сошло с рук, что он списал на тесте."),
                        ex("You won't get away with this!", "Тебе это с рук не сойдёт!"),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "bring_about",
                verb = "bring",
                particle = "about",
                isSeparable = true,
                category = PhrasalVerbCategory.GENERAL,
                meanings = listOf(
                    meaning(
                        "to cause something to happen",
                        "вызвать, привести к",
                        ex(
                            "The new law brought about significant changes.",
                            "Новый закон привёл к значительным изменениям.",
                        ),
                        ex("What brought about this sudden decision?", "Что привело к этому внезапному решению?"),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "cut_down_on",
                verb = "cut",
                particle = "down on",
                isSeparable = false,
                category = PhrasalVerbCategory.GENERAL,
                meanings = listOf(
                    meaning(
                        "to reduce the amount of something you do or consume",
                        "сократить, уменьшить потребление",
                        ex("I'm trying to cut down on sugar.", "Я стараюсь сократить потребление сахара."),
                        ex("The company cut down on unnecessary expenses.", "Компания сократила ненужные расходы."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "end_up",
                verb = "end",
                particle = "up",
                isSeparable = false,
                category = PhrasalVerbCategory.GENERAL,
                meanings = listOf(
                    meaning(
                        "to finally be in a situation or place, often unexpectedly",
                        "оказаться, в итоге стать",
                        ex(
                            "We got lost and ended up in the wrong city.",
                            "Мы заблудились и в итоге оказались не в том городе.",
                        ),
                        ex("He ended up becoming a doctor after all.", "В итоге он всё-таки стал врачом."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "take_up",
                verb = "take",
                particle = "up",
                isSeparable = true,
                category = PhrasalVerbCategory.GENERAL,
                meanings = listOf(
                    meaning(
                        "to start a new hobby or activity",
                        "заняться, начать заниматься",
                        ex("She took up painting after she retired.", "После выхода на пенсию она занялась живописью."),
                        ex("He decided to take up jogging.", "Он решил заняться бегом."),
                    ),
                    meaning(
                        "to occupy time, space, or resources",
                        "занимать, отнимать",
                        ex(
                            "This project takes up most of my time.",
                            "Этот проект занимает большую часть моего времени.",
                        ),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "stand_out",
                verb = "stand",
                particle = "out",
                isSeparable = false,
                category = PhrasalVerbCategory.GENERAL,
                meanings = listOf(
                    meaning(
                        "to be very noticeable or clearly better than others",
                        "выделяться, бросаться в глаза",
                        ex(
                            "Her bright red coat made her stand out in the crowd.",
                            "Её ярко-красное пальто заставляло её выделяться в толпе.",
                        ),
                        ex(
                            "This candidate really stands out from the others.",
                            "Этот кандидат действительно выделяется на фоне остальных.",
                        ),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "hold_on",
                verb = "hold",
                particle = "on",
                isSeparable = false,
                category = PhrasalVerbCategory.GENERAL,
                meanings = listOf(
                    meaning(
                        "to wait for a short time",
                        "подождать, обождать",
                        ex("Hold on, I'll be right with you.", "Подождите, я сейчас."),
                        ex("Hold on a second — let me check.", "Одну секунду — дайте проверю."),
                    ),
                    meaning(
                        "to grip or keep hold of something firmly",
                        "держаться, цепляться",
                        ex("Hold on tight — the road is bumpy.", "Держитесь крепче — дорога ухабистая."),
                    ),
                ),
            ),
            PhrasalVerb(
                id = "get_rid_of",
                verb = "get",
                particle = "rid of",
                isSeparable = false,
                category = PhrasalVerbCategory.GENERAL,
                meanings = listOf(
                    meaning(
                        "to remove, dispose of, or free yourself from something unwanted",
                        "избавиться от",
                        ex("She got rid of all the old furniture.", "Она избавилась от всей старой мебели."),
                        ex(
                            "How do I get rid of this error message?",
                            "Как мне избавиться от этого сообщения об ошибке?",
                        ),
                    ),
                ),
            ),
        )
    }
}
