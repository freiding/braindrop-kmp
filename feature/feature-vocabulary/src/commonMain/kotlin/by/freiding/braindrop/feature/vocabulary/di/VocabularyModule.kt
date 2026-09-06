package by.freiding.braindrop.feature.vocabulary.di

import by.freiding.braindrop.feature.vocabulary.data.datasource.LocalChunkDataSource
import by.freiding.braindrop.feature.vocabulary.data.datasource.LocalChunkProgressDataSource
import by.freiding.braindrop.feature.vocabulary.data.repository.ChunkRepositoryImpl
import by.freiding.braindrop.feature.vocabulary.domain.model.VocabularyQuizMode
import by.freiding.braindrop.feature.vocabulary.domain.repository.ChunkRepository
import by.freiding.braindrop.feature.vocabulary.domain.usecase.EvaluateVocabularyAnswerUseCase
import by.freiding.braindrop.feature.vocabulary.domain.usecase.GenerateVocabularyQuizUseCase
import by.freiding.braindrop.feature.vocabulary.domain.usecase.GetChunkDetailUseCase
import by.freiding.braindrop.feature.vocabulary.domain.usecase.GetChunksUseCase
import by.freiding.braindrop.feature.vocabulary.domain.usecase.GetDueChunksUseCase
import by.freiding.braindrop.feature.vocabulary.domain.usecase.GetVocabularyStreakDaysUseCase
import by.freiding.braindrop.feature.vocabulary.domain.usecase.GradeChunkUseCase
import by.freiding.braindrop.feature.vocabulary.domain.usecase.PreviewRecallIntervalsUseCase
import by.freiding.braindrop.feature.vocabulary.domain.usecase.SetChunkLearnedUseCase
import by.freiding.braindrop.feature.vocabulary.domain.usecase.SubmitVocabularyQuizAnswerUseCase
import by.freiding.braindrop.feature.vocabulary.presentation.detail.VocabularyChunkDetailViewModel
import by.freiding.braindrop.feature.vocabulary.presentation.list.VocabularyListViewModel
import by.freiding.braindrop.feature.vocabulary.presentation.quiz.VocabularyQuizViewModel
import by.freiding.braindrop.feature.vocabulary.presentation.session.VocabularyCardSessionViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val vocabularyModule = module {
    factory { LocalChunkDataSource() }
    factory { LocalChunkProgressDataSource(get()) }
    factory<ChunkRepository> { ChunkRepositoryImpl(get(), get(), get(), get()) }

    factory { GetChunksUseCase(get()) }
    factory { GetChunkDetailUseCase(get()) }
    factory { GetDueChunksUseCase(get()) }
    factory { GradeChunkUseCase(get()) }
    factory { SetChunkLearnedUseCase(get()) }
    factory { GenerateVocabularyQuizUseCase(get()) }
    factory { EvaluateVocabularyAnswerUseCase() }
    factory { SubmitVocabularyQuizAnswerUseCase(get()) }
    factory { GetVocabularyStreakDaysUseCase(get()) }
    factory { PreviewRecallIntervalsUseCase() }

    viewModel { VocabularyListViewModel(get()) }
    viewModel { (chunkId: String) -> VocabularyChunkDetailViewModel(chunkId, get(), get(), get()) }
    viewModel { VocabularyCardSessionViewModel(get(), get(), get()) }
    viewModel { (mode: VocabularyQuizMode) -> VocabularyQuizViewModel(mode, get(), get(), get(), get()) }
}
