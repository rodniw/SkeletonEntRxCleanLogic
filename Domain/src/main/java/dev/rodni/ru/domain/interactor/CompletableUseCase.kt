package dev.rodni.ru.domain.interactor

import dev.rodni.ru.domain.executor.PostExecutionThread
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers

/**
 * base completable use case abstract class
 */
abstract class CompletableUseCase<in Params> constructor(
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
    protected abstract fun buildUseCaseCompletable(params: Params? = null): Completable

    /**
     * execution method
     */
    open fun execute(obseerver: DisposableCompletableObserver, params: Params? = null) {
        val completable = this.buildUseCaseCompletable(params)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutionThread.scheduler)
        addDisposable(completable.subscribeWith(obseerver))
    }

    /**
     * a way to add a disposable into the composite disposable
     */
    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    /**
     * deletes disposables
     */
    fun dispose() {
        disposables.dispose()
    }
}