import mainGameCompo from "@/components/Main/MainGameCompo.vue";
import reviewListCompo from "@/components/Review/ReviewListCompo.vue";
import loginCompo from "@/components/User/LoginCompo.vue";
import signUpCompo from "@/components/User/SignUpCompo.vue";
import MiddleCompo from "@/components/MiddleCompo.vue";
import reviewWriteCompo from "@/components/Review/ReviewWriteCompo.vue";
import reviewDetailCompo from "@/components/Review/ReviewDetailCompo.vue";

export default [
    {
        path: '/',
        component: MiddleCompo,
        children: [
            {
                path: '/main',
                component: mainGameCompo,
                alias: ['/'],
            },
            {
                path: '/review',
                component: reviewListCompo,
            },
            {
                path: '/review/write',
                component: reviewWriteCompo,
            },
            {
                path: '/review/detail/:id',
                component: reviewDetailCompo,
            },
            {
                path: '/login',
                component: loginCompo,
            },
            {
                path: '/sign-up',
                component: signUpCompo,
            },
        ]
    },
]