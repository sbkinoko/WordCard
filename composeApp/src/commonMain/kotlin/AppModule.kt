import org.koin.dsl.module
import repository.detail.DetailRepository
import repository.detail.DetailRepositoryImpl
import repository.detailorder.DetailOrderRepository
import repository.detailorder.DetailOrderRepositoryImpl
import repository.screentype.ScreenTypeRepository
import repository.screentype.ScreenTypeRepositoryImpl
import repository.title.TitleRepository
import repository.title.TitleRepositoryImpl
import usecase.additem.AddItemUseCase
import usecase.additem.AddItemUseCaseImpl
import usecase.deleteitem.DeleteItemUseCase
import usecase.deleteitem.DeleteItemUseCaseImpl
import usecase.getitemorder.GetItemOrderUseCase
import usecase.getitemorder.GetItemOrderUseCaseImpl
import usecase.moveitem.MoveItemUseCase
import usecase.moveitem.MoveItemUseCaseImpl
import viewmodel.detail.DetailViewModel
import viewmodel.detail.EditViewModel
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

    single<AddItemUseCase> {
        AddItemUseCaseImpl(
            screenTypeRepository = get(),
            detailRepository = get(),
            detailOrderRepository = get(),
        )
    }

    single<DeleteItemUseCase> {
        DeleteItemUseCaseImpl(
            detailRepository = get(),
            detailOrderRepository = get(),
            screenTypeRepository = get(),
        )
    }

    single<MoveItemUseCase> {
        MoveItemUseCaseImpl(
            detailOrderRepository = get(),
            screenTypeRepository = get(),
        )
    }

    single<GetItemOrderUseCase> {
        GetItemOrderUseCaseImpl(
            detailOrderRepository = get(),
            screenTypeRepository = get(),
        )
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

    single {
        EditViewModel()
    }
}
