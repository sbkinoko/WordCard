import org.koin.dsl.module
import repository.detail.DetailRepository
import repository.detail.DetailRepositoryImpl
import repository.screentype.ScreenTypeRepository
import repository.screentype.ScreenTypeRepositoryImpl
import repository.title.TitleRepository
import repository.title.TitleRepositoryImpl
import viewmodel.DetailViewModel
import viewmodel.TestViewModel
import viewmodel.TopViewModel

val AppModule = module {
    single<ScreenTypeRepository> {
        ScreenTypeRepositoryImpl()
    }

    single<TitleRepository> {
        TitleRepositoryImpl()
    }

    single<DetailRepository> {
        DetailRepositoryImpl()
    }

    single {
        TopViewModel()
    }

    single {
        DetailViewModel()
    }

    single {
        TestViewModel()
    }
}
