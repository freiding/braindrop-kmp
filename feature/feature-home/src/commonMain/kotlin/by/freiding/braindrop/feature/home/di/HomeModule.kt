package by.freiding.braindrop.feature.home.di

import by.freiding.braindrop.feature.home.data.datasource.LocalStudyCategoryDataSource
import by.freiding.braindrop.feature.home.data.datasource.LocalStudyProgressDataSource
import by.freiding.braindrop.feature.home.data.repository.StudyCategoryRepositoryImpl
import by.freiding.braindrop.feature.home.domain.repository.StudyCategoryRepository
import by.freiding.braindrop.feature.home.domain.usecase.GetStudyCategoriesUseCase
import by.freiding.braindrop.feature.home.presentation.viewmodel.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    single { LocalStudyCategoryDataSource() }
    single { LocalStudyProgressDataSource(get(), get()) }
    factory<StudyCategoryRepository> { StudyCategoryRepositoryImpl(get(), get()) }
    factory { GetStudyCategoriesUseCase(get()) }
    viewModel { HomeViewModel(get()) }
}
