package by.freiding.braindrop.feature.tenses.di

import by.freiding.braindrop.feature.tenses.data.datasource.LocalTenseDataSource
import by.freiding.braindrop.feature.tenses.data.datasource.LocalTenseProgressDataSource
import by.freiding.braindrop.feature.tenses.data.repository.TenseRepositoryImpl
import by.freiding.braindrop.feature.tenses.domain.model.TenseQuizType
import by.freiding.braindrop.feature.tenses.domain.repository.TenseRepository
import by.freiding.braindrop.feature.tenses.domain.usecase.GenerateTenseQuizUseCase
import by.freiding.braindrop.feature.tenses.domain.usecase.GetComparisonsUseCase
import by.freiding.braindrop.feature.tenses.domain.usecase.GetStreakDaysUseCase
import by.freiding.braindrop.feature.tenses.domain.usecase.GetTenseDetailUseCase
import by.freiding.braindrop.feature.tenses.domain.usecase.GetTensesUseCase
import by.freiding.braindrop.feature.tenses.domain.usecase.SubmitTenseQuizAnswerUseCase
import by.freiding.braindrop.feature.tenses.domain.usecase.ToggleTenseLearnedUseCase
import by.freiding.braindrop.feature.tenses.presentation.cheatsheet.TenseCheatSheetViewModel
import by.freiding.braindrop.feature.tenses.presentation.comparison.TenseComparisonsViewModel
import by.freiding.braindrop.feature.tenses.presentation.detail.TenseDetailViewModel
import by.freiding.braindrop.feature.tenses.presentation.list.TensesListViewModel
import by.freiding.braindrop.feature.tenses.presentation.quiz.TensesQuizViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val tensesModule = module {
    single { LocalTenseDataSource() }
    single { LocalTenseProgressDataSource(get()) }
    factory<TenseRepository> { TenseRepositoryImpl(get(), get(), get(), get()) }

    factory { GetTensesUseCase(get()) }
    factory { GetTenseDetailUseCase(get()) }
    factory { GetComparisonsUseCase(get()) }
    factory { ToggleTenseLearnedUseCase(get()) }
    factory { GenerateTenseQuizUseCase(get()) }
    factory { SubmitTenseQuizAnswerUseCase(get()) }
    factory { GetStreakDaysUseCase(get()) }

    viewModel { TensesListViewModel(get()) }
    viewModel { (tenseId: String) -> TenseDetailViewModel(tenseId, get(), get()) }
    viewModel { TenseComparisonsViewModel(get()) }
    viewModel { TenseCheatSheetViewModel(get()) }
    viewModel { (quizType: TenseQuizType) -> TensesQuizViewModel(quizType, get(), get(), get()) }
}
