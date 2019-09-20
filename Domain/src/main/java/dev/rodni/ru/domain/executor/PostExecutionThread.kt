package dev.rodni.ru.domain.executor

import io.reactivex.Scheduler

/**
 * this interface to override in a ui module layer
 * makes a decision which thread to use to show a data in an user interface
 */
interface PostExecutionThread {
    val scheduler: Scheduler
}