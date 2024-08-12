import mainGameCompo from "@/components/Main/MainGameCompo.vue";
import reviewListCompo from "@/components/Review/ReviewListCompo.vue";
import loginCompo from "@/components/User/LoginCompo.vue";
import signUpCompo from "@/components/User/SignUpCompo.vue";
import MiddleCompo from "@/components/MiddleCompo.vue";
import reviewWriteCompo from "@/components/Review/ReviewWriteCompo.vue";
import reviewDetailCompo from "@/components/Review/ReviewDetailCompo.vue";
import MyLikeReview from "@/components/Review/MyLikeReview.vue";
import MyPageCompo from "@/components/MyPage/MyPageCompo.vue";
import AccountDelete from "@/components/MyPage/AccountDelete.vue";
import MyCourseCompo from "@/components/MyPage/MyCourseCompo.vue";
import LikeCourseCompo from "@/components/MyPage/LikeCourseCompo.vue";

export default [
  {
    path: '/',
    component: MiddleCompo,
    children: [
      {
        path: 'main',
        component: mainGameCompo,
        alias: ['/'],
      },
      {
        path: 'review',
        component: reviewListCompo,
      },
      {
        path: 'review/write',
        component: reviewWriteCompo,
      },
      {
        path: 'review/detail/:id',
        component: reviewDetailCompo,
      },
      {
        path: 'login',
        component: loginCompo,
      },
      {
        path: 'sign-up',
        component: signUpCompo,
      },
      {
        path: 'myLikeReview',
        component: MyLikeReview,
      },
      {
        path: 'mypage',
        component: MyPageCompo,
        children: [
          {
            path: 'mycourse',
            component: MyCourseCompo, // 예시로, 실제 컴포넌트를 사용해야 합니다.
          },
          {
            path: 'likecourse',
            component: LikeCourseCompo, // 예시로, 실제 컴포넌트를 사용해야 합니다.
          },
          {
            path: 'delete',
            component: AccountDelete,
          }
        ]
      }
    ]
  }
];
