import org.koin.dsl.module
import repository.detail.DetailRepository
import repository.detail.DetailRepositoryImpl
import repository.detailorder.DetailOrderRepository
import repository.detailorder.DetailOrderRepositoryImpl
import repository.screentype.ScreenTypeRepository
import repository.screentype.ScreenTypeRepositoryImpl
import repository.title.TitleRepository
import repository.title.TitleRepositoryImpl
import repository.titleorder.TitleOrderRepository
import repository.titleorder.TitleOrderRepositoryImpl
import usecase.additem.AddItemUseCase
import usecase.additem.AddItemUseCaseImpl
import usecase.deleteitem.DeleteItemUseCase
import usecase.deleteitem.DeleteItemUseCaseImpl
import usecase.deletetitle.DeleteTitleUseCase
import usecase.deletetitle.DeleteTitleUseCaseImpl
import usecase.getitemorder.GetIOrderedItemUseCase
import usecase.getitemorder.GetIOrderedItemUseCaseImpl
import usecase.getorder.GetItemIndexUseCase
import usecase.getorder.GetItemIndexUseCaseImpl
import usecase.moveitem.MoveItemUseCase
import usecase.moveitem.MoveItemUseCaseImpl
import usecase.movetitle.MoveTitleUseCase
import usecase.movetitle.MoveTitleUseCaseImpl
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

    single<TitleOrderRepository> {
        TitleOrderRepositoryImpl()
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

    single<MoveTitleUseCase> {
        MoveTitleUseCaseImpl(
            titleOrderRepository = get(),
        )
    }

    single<GetIOrderedItemUseCase> {
        GetIOrderedItemUseCaseImpl(
            detailOrderRepository = get(),
            screenTypeRepository = get(),
            detailRepository = get(),
        )
    }

    single<GetItemIndexUseCase> {
        GetItemIndexUseCaseImpl(
            detailOrderRepository = get(),
            screenTypeRepository = get(),
        )
    }

    single<DeleteTitleUseCase>{
        DeleteTitleUseCaseImpl(
            titleRepository = get(),
            titleOrderRepository = get(),
            detailRepository = get(),
            detailOrderRepository = get(),
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
