package by.freiding.braindrop.feature.profile.di

import by.freiding.braindrop.feature.profile.data.datasource.ProgressDataSource
import by.freiding.braindrop.feature.profile.data.repository.ProgressRepositoryImpl
import by.freiding.braindrop.feature.profile.domain.repository.ProgressRepository
import by.freiding.braindrop.feature.profile.domain.usecase.GetProgressDataUseCase
import by.freiding.braindrop.feature.profile.presentation.viewmodel.ProgressViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {
    factory { ProgressDataSource(get(), get(), get()) }
    factory<ProgressRepository> { ProgressRepositoryImpl(get(), get()) }
    factory { GetProgressDataUseCase(get()) }
    viewModel { ProgressViewModel(get()) }
}
