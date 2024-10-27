import org.koin.dsl.module
import repository.detail.DetailRepository
import repository.detail.DetailRepositoryImpl
import repository.detailorder.DetailOrderRepository
import repository.detailorder.DetailOrderRepositoryImpl
import repository.screentype.ScreenTypeRepository
import repository.screentype.ScreenTypeRepositoryImpl
import repository.title.TitleRepository
import repository.title.TitleRepositoryImpl
import viewmodel.detail.DetailViewModel
import viewmodel.detail.TestViewModel
import viewmodel.top.TopViewModel

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

    single<DetailOrderRepository> {
        DetailOrderRepositoryImpl()
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
