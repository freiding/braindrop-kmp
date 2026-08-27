package by.freiding.braindrop.feature.phrasalverbs.di

import by.freiding.braindrop.feature.phrasalverbs.data.datasource.LocalPhrasalVerbDataSource
import by.freiding.braindrop.feature.phrasalverbs.data.datasource.LocalPhrasalVerbProgressDataSource
import by.freiding.braindrop.feature.phrasalverbs.data.repository.PhrasalVerbRepositoryImpl
import by.freiding.braindrop.feature.phrasalverbs.domain.model.PhrasalVerbQuizType
import by.freiding.braindrop.feature.phrasalverbs.domain.repository.PhrasalVerbRepository
import by.freiding.braindrop.feature.phrasalverbs.domain.usecase.GeneratePhrasalVerbQuizUseCase
import by.freiding.braindrop.feature.phrasalverbs.domain.usecase.GetPhrasalVerbDetailUseCase
import by.freiding.braindrop.feature.phrasalverbs.domain.usecase.GetPhrasalVerbStreakDaysUseCase
import by.freiding.braindrop.feature.phrasalverbs.domain.usecase.GetPhrasalVerbsUseCase
import by.freiding.braindrop.feature.phrasalverbs.domain.usecase.SubmitPhrasalVerbQuizAnswerUseCase
import by.freiding.braindrop.feature.phrasalverbs.domain.usecase.TogglePhrasalVerbLearnedUseCase
import by.freiding.braindrop.feature.phrasalverbs.presentation.detail.PhrasalVerbDetailViewModel
import by.freiding.braindrop.feature.phrasalverbs.presentation.list.PhrasalVerbsListViewModel
import by.freiding.braindrop.feature.phrasalverbs.presentation.quiz.PhrasalVerbsQuizViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val phrasalVerbsModule = module {
    single { LocalPhrasalVerbDataSource() }
    single { LocalPhrasalVerbProgressDataSource(get()) }
    factory<PhrasalVerbRepository> { PhrasalVerbRepositoryImpl(get(), get(), get(), get()) }

    factory { GetPhrasalVerbsUseCase(get()) }
    factory { GetPhrasalVerbDetailUseCase(get()) }
    factory { TogglePhrasalVerbLearnedUseCase(get()) }
    factory { GeneratePhrasalVerbQuizUseCase(get()) }
    factory { SubmitPhrasalVerbQuizAnswerUseCase(get()) }
    factory { GetPhrasalVerbStreakDaysUseCase(get()) }

    viewModel { PhrasalVerbsListViewModel(get(), get()) }
    viewModel { (verbId: String) -> PhrasalVerbDetailViewModel(verbId, get(), get()) }
    viewModel { (quizType: PhrasalVerbQuizType) -> PhrasalVerbsQuizViewModel(quizType, get(), get(), get()) }
}
