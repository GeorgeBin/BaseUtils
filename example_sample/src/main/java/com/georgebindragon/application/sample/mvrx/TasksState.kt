// package com.georgebindragon.application.sample.mvrx
//
// import com.airbnb.mvrx.Async
// import com.airbnb.mvrx.MvRxState
// import com.airbnb.mvrx.Uninitialized
// import java.util.UUID
//
// /**
//  *
//  * 创建人：George
//  * 类名称：TasksState
//  * 类概述：
//  * 详细描述：
//  *
//  *
//  * 修改人：
//  * 修改时间：
//  * 修改备注：
//  */
//
//
// data class TasksState(
//         val tasks: List<Task> = emptyList(),
//         val taskRequest: Async<List<Task>> = Uninitialized,
//         val isLoading: Boolean = false,
//         val lastEditedTask: String? = null
// ) : MvRxState //MvRxState 仅是一个标记接口
//
// //待办事的定义，包含有id, title, description以及是否完成标志complete
// data class Task(
//         var title: String = "",
//         var description: String = "",
//         var id: String = UUID.randomUUID().toString(),
//         var complete: Boolean = false
// )