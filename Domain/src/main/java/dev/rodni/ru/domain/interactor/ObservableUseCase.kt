package dev.rodni.ru.domain.interactor

import dev.rodni.ru.domain.executor.PostExecutionThread
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 * base observable use case abstract class
 */
abstract class ObservableUseCase<T, in Params> constructor(
    private val postExecutionThread: PostExecutionThread
) {

    /**
     * composite disposable object for handling connection with disposables
     * and switch them off when a ui not visible anymore
     */
    private val disposables = CompositeDisposable()

    /**
     * method for building use case with params
     */
    protected abstract fun buildUseCaseObservable(params: Params? = null): Flowable<T>

    /**
     * execution method
     */
    open fun execute(observer: DisposableObserver<T>, params: Params? = null) {
        val observable = this.buildUseCaseObservable(params)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutionThread.scheduler)
        addDisposable(observable.toObservable().subscribeWith(observer))
    }

    /**
     * a way to add a disposable into the composite disposable
     */
    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    /**
     * deleting disposables
     */
    fun dispose() {
        disposables.dispose()
    }
}