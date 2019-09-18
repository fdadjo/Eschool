import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'category',
                loadChildren: './category/category.module#EschoolCategoryModule'
            },
            {
                path: 'abscence',
                loadChildren: './abscence/abscence.module#EschoolAbscenceModule'
            },
            {
                path: 'classroom',
                loadChildren: './classroom/classroom.module#EschoolClassroomModule'
            },
            {
                path: 'classroom-history',
                loadChildren: './classroom-history/classroom-history.module#EschoolClassroomHistoryModule'
            },
            {
                path: 'exam',
                loadChildren: './exam/exam.module#EschoolExamModule'
            },
            {
                path: 'exam-result',
                loadChildren: './exam-result/exam-result.module#EschoolExamResultModule'
            },
            {
                path: 'homework',
                loadChildren: './homework/homework.module#EschoolHomeworkModule'
            },
            {
                path: 'job',
                loadChildren: './job/job.module#EschoolJobModule'
            },
            {
                path: 'job-history',
                loadChildren: './job-history/job-history.module#EschoolJobHistoryModule'
            },
            {
                path: 'lesson',
                loadChildren: './lesson/lesson.module#EschoolLessonModule'
            },
            {
                path: 'lesson-history',
                loadChildren: './lesson-history/lesson-history.module#EschoolLessonHistoryModule'
            },
            {
                path: 'lesson-history-preparation',
                loadChildren: './lesson-history-preparation/lesson-history-preparation.module#EschoolLessonHistoryPreparationModule'
            },
            {
                path: 'message',
                loadChildren: './message/message.module#EschoolMessageModule'
            },
            {
                path: 'payment',
                loadChildren: './payment/payment.module#EschoolPaymentModule'
            },
            {
                path: 'school',
                loadChildren: './school/school.module#EschoolSchoolModule'
            },
            {
                path: 'school-authority',
                loadChildren: './school-authority/school-authority.module#EschoolSchoolAuthorityModule'
            },
            {
                path: 'student-homework',
                loadChildren: './student-homework/student-homework.module#EschoolStudentHomeworkModule'
            },
            {
                path: 'time-sheet',
                loadChildren: './time-sheet/time-sheet.module#EschoolTimeSheetModule'
            },
            {
                path: 'time-slot',
                loadChildren: './time-slot/time-slot.module#EschoolTimeSlotModule'
            },
            {
                path: 'time-slot-history',
                loadChildren: './time-slot-history/time-slot-history.module#EschoolTimeSlotHistoryModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EschoolEntityModule {}
