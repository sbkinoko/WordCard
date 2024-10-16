import org.koin.dsl.module
import repository.screentype.ScreenTypeRepository
import repository.screentype.ScreenTypeRepositoryImpl
import viewmodel.DetailViewModel
import viewmodel.TestViewModel
import viewmodel.TopViewModel

val AppModule = module {
    single<ScreenTypeRepository> {
        ScreenTypeRepositoryImpl()
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
