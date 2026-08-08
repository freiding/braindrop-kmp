package by.freiding.braindrop.feature.irregularverbs.di

import by.freiding.braindrop.feature.irregularverbs.data.datasource.LocalIrregularVerbDataSource
import by.freiding.braindrop.feature.irregularverbs.data.datasource.LocalVerbProgressDataSource
import by.freiding.braindrop.feature.irregularverbs.data.repository.IrregularVerbRepositoryImpl
import by.freiding.braindrop.feature.irregularverbs.domain.model.QuizType
import by.freiding.braindrop.feature.irregularverbs.domain.repository.IrregularVerbRepository
import by.freiding.braindrop.feature.irregularverbs.domain.usecase.GenerateQuizUseCase
import by.freiding.braindrop.feature.irregularverbs.domain.usecase.GetStreakDaysUseCase
import by.freiding.braindrop.feature.irregularverbs.domain.usecase.GetVerbDetailUseCase
import by.freiding.braindrop.feature.irregularverbs.domain.usecase.GetVerbsUseCase
import by.freiding.braindrop.feature.irregularverbs.domain.usecase.SubmitQuizAnswerUseCase
import by.freiding.braindrop.feature.irregularverbs.domain.usecase.ToggleVerbLearnedUseCase
import by.freiding.braindrop.feature.irregularverbs.presentation.detail.VerbDetailViewModel
import by.freiding.braindrop.feature.irregularverbs.presentation.list.VerbListViewModel
import by.freiding.braindrop.feature.irregularverbs.presentation.quiz.QuizViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val irregularVerbsModule = module {
    single { LocalIrregularVerbDataSource() }
    single { LocalVerbProgressDataSource(get()) }
    factory<IrregularVerbRepository> { IrregularVerbRepositoryImpl(get(), get(), get(), get()) }

    factory { GetVerbsUseCase(get()) }
    factory { GetVerbDetailUseCase(get()) }
    factory { ToggleVerbLearnedUseCase(get()) }
    factory { GenerateQuizUseCase(get()) }
    factory { SubmitQuizAnswerUseCase(get()) }
    factory { GetStreakDaysUseCase(get()) }

    viewModel { VerbListViewModel(get(), get()) }
    viewModel { (verbId: String) -> VerbDetailViewModel(verbId, get(), get()) }
    viewModel { (quizType: QuizType) -> QuizViewModel(quizType, get(), get(), get()) }
}
