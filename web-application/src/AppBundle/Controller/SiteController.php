<?php

namespace AppBundle\Controller;

use AppBundle\Entity\Citizen;
use AppBundle\Entity\Refugee;
use AppBundle\Service\RoleService;
use Doctrine\ORM\QueryBuilder;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Request;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Template;
use Symfony\Component\HttpKernel\Exception\AccessDeniedHttpException;
use Symfony\Component\HttpKernel\Exception\NotFoundHttpException;
use Zantolov\AppBundle\Entity\User;
use Zantolov\BlogBundle\Entity\Category;
use Zantolov\BlogBundle\Entity\Post;
use Zantolov\BlogBundle\Repository\PostRepository;

/**
 * Class DefaultController
 * @package AppBundle\Controller
 */
class SiteController extends Controller
{
    /**
     * @Route("/", name="homepage")
     * @Template
     * @param Request $request
     * @return \Symfony\Component\HttpFoundation\Response
     */
    public function indexAction(Request $request)
    {
        /** @var QueryBuilder $posts */
        $posts = $this->getDoctrine()->getRepository('ZantolovBlogBundle:Post')->getActivePostsQueryBuilder();
        $posts->setMaxResults(3);
        $posts = $posts->getQuery()->getResult();

        $offers = $this->getDoctrine()->getManager()->getRepository('AppBundle:Offer')->findBy(['active' => true]);
        $requests = $this->getDoctrine()->getManager()->getRepository('AppBundle:Request')->findBy(['active' => true]);

        return compact('posts','offers','requests');
    }


    /**
     * @Route("/setLocale/{locale}", name="site.setLocale")
     * @Template
     * @param Request $request
     * @return \Symfony\Component\HttpFoundation\Response
     */
    public function changeLocaleAction($locale)
    {
        $this->get('session')->set('_locale', $locale);
        return $this->redirectToRoute('homepage');
    }


    /**
     * @Route("/p/{slug}", name="site.post")
     * @Template
     * @param Request $request
     * @return \Symfony\Component\HttpFoundation\Response
     */
    public function postDetailsAction($slug)
    {
        $em = $this->getDoctrine()->getManager();

        /** @var PostRepository $postRepo */
        $postRepo = $em->getRepository('ZantolovBlogBundle:Post');
        $post = $postRepo->getPostBySlug($slug);
        if (empty($post)) {
            throw $this->createNotFoundException();
        }
        $post = $post[0];
        return compact('post');
    }


    /**
     * @Route("/c/{slug}", name="site.category")
     * @Template
     * @param Request $request
     * @return \Symfony\Component\HttpFoundation\Response
     */
    public function categoryIndexAction(Request $request, $slug)
    {
        $em = $this->getDoctrine()->getManager();

        /** @var Category $category */
        $category = $em->getRepository('ZantolovBlogBundle:Category')->findOneBy(array(
            'active' => true,
            'slug'   => $slug,
        ));

        if (empty($category)) {
            throw $this->createNotFoundException();
        }

        /** @var PostRepository $postRepo */
        $postRepo = $em->getRepository('ZantolovBlogBundle:Post');
        $posts = $postRepo->getPostsByCategory($category);

        uasort($posts, function (Post $a, Post $b) {
            if ($a->getPublishedAt() == $b->getPublishedAt()) {
                return 0;
            }
            return ($a->getPublishedAt() > $b->getPublishedAt()) ? -1 : 1;
        });

        $paginator = $this->get('knp_paginator');
        $posts = $paginator->paginate($posts, $request->query->getInt('page', 1)/*page number*/, 9);

        return compact('category', 'posts');
    }

    /**
     * @Route("/404", name="404")
     * @Template
     * @param Request $request
     * @return \Symfony\Component\HttpFoundation\Response
     */
    public function error404Action(Request $request)
    {
        return [];
    }


    /**
     * @Route("/widget", name="widget")
     * @Template
     * @param Request $request
     * @return \Symfony\Component\HttpFoundation\Response
     */
    public function widgetAction(Request $request)
    {
        return [];
    }

    /**
     * @Route("/help", name="help")
     * @Template
     * @param Request $request
     * @return \Symfony\Component\HttpFoundation\Response
     */
    public function helpAction(Request $request)
    {
        $user = $this->getUser();
        $role = null;

        if (!empty($user)) {
            /** @var RoleService $roleService */
            $roleService = $this->get('role_service');
            $profiles = $roleService->getUserRole($user);

            $data = [];
            if (!empty($profiles['profile_refugee'])) {
                $role = 'refugee';

                //refugee requests and accepted offers
                $data['acceptedOffers'] = $this->getDoctrine()->getManager()->getRepository('AppBundle:Offer')->findBy([
                    'refugee' => $profiles['profile_refugee']->getId(),
                ]);

                $data['submittedRequests'] = $this->getDoctrine()->getManager()->getRepository('AppBundle:Request')->findBy([
                    'refugee' => $profiles['profile_refugee']->getId(),
                ]);


            } elseif (!empty($profiles['profile_citizen'])) {
                $role = 'citizen';

                //refugee requests and accepted offers
                $data['submittedOffers'] = $this->getDoctrine()->getManager()->getRepository('AppBundle:Offer')->findBy([
                    'citizen' => $profiles['profile_citizen']->getId(),
                ]);

                $data['acceptedRequests'] = $this->getDoctrine()->getManager()->getRepository('AppBundle:Request')->findBy([
                    'citizen' => $profiles['profile_citizen']->getId(),
                ]);

            }
        }

        return compact('role', 'data');
    }

    /**
     * @Route("/tips", name="tips")
     * @Template
     * @param Request $request
     * @return \Symfony\Component\HttpFoundation\Response
     */
    public function tipsAction(Request $request)
    {
        return [];
    }

    /**
     * @Route("/legal", name="legal")
     * @Template
     * @param Request $request
     * @return \Symfony\Component\HttpFoundation\Response
     */
    public function legalAction(Request $request)
    {
        return [];
    }

    /**
     * @Route("/info/tips", name="info-tips")
     * @Template
     * @param Request $request
     * @return \Symfony\Component\HttpFoundation\Response
     */
    public function infoTipsAction(Request $request)
    {
        return [];
    }

    /**
     * @Route("/info/legal", name="info-legal")
     * @Template
     * @param Request $request
     * @return \Symfony\Component\HttpFoundation\Response
     */
    public function infoLegalAction(Request $request)
    {
        return [];
    }

    /**
     *
     * @Route("authority/profile/check/{id}", name="site.profile.check")
     * @Method("GET")
     * @Template
     */
    public function profileCheckAction($id)
    {
        /** @var User $user */
        $user = $this->get('security.token_storage')->getToken()->getUser();

        if (!($user instanceof User)) {
            throw new AccessDeniedHttpException();
        }

        /** @var RoleService $roleService */
        $roleService = $this->get('role_service');
        $profiles = $roleService->getUserRole($user);

        if (empty($profiles['profile_authority'])) {
            throw new AccessDeniedHttpException();
        }

        $refugee = $this->getDoctrine()->getRepository('AppBundle:Refugee')->find($id);

        return compact('refugee');

    }


    /**
     * @Route("/s/", name="site.search")
     * @Template
     * @param Request $request
     */
    public function searchAction(Request $request)
    {
        $query = $request->get('query');

        $em = $this->getDoctrine()->getManager();

        /** @var PostRepository $postRepo */
        $postRepo = $em->getRepository('ZantolovBlogBundle:Post');
        $posts = $postRepo->getActivePostsQueryBuilder()
            ->andWhere('p.body LIKE :query')
            ->setParameter('query', "%$query%")
            ->orWhere('p.intro LIKE :query')
            ->setParameter('query', "%$query%")
            ->orWhere('p.title LIKE :query')
            ->setParameter('query', "%$query%")
            ->getQuery()
            ->getResult();

        uasort($posts, function (Post $a, Post $b) {
            if ($a->getPublishedAt() == $b->getPublishedAt()) {
                return 0;
            }
            return ($a->getPublishedAt() > $b->getPublishedAt()) ? -1 : 1;
        });

        $paginator = $this->get('knp_paginator');
        $posts = $paginator->paginate($posts, $request->query->getInt('page', 1)/*page number*/, 9);

        return compact('posts');

    }


    /**
     * @param $limit
     * @Template()
     */
    public function missingAction($limit)
    {
        $missing = $this->getDoctrine()->getRepository('AppBundle:Refugee')->findBy([
            'active'    => true,
            'isMissing' => true,
        ]);

        $missing = array_slice($missing, 0, $limit);

        return compact('missing');
    }


    /**
     * @Route("/setup/{type}", name="site.acc.setup")
     * @Template()
     */
    public function setupAction(Request $request, $type)
    {
        $user = $this->getUser();

        /** @var RoleService $roleService */
        $roleService = $this->get('role_service');
        $profiles = $roleService->getUserRole($user);


        if (!empty($profiles['profile_refugee'])) {
            return $this->redirectToRoute('site.refugees');
        } elseif (!empty($profiles['profile_citizen'])) {
            return $this->redirectToRoute('site.citizens');
        } elseif (!empty($profiles['profile_authority'])) {
            return $this->redirectToRoute('admin_refugeess.index');
        }


        if ($request->getMethod() == 'GET') {
            return compact('type');
        } elseif ($request->getMethod() == 'POST') {

            switch ($type) {
                case 'refugee':
                    $r = new Refugee();
                    $r->setFirstName($request->get('firstName'));
                    $r->setLastName($request->get('lastName'));
                    $r->setCountry($request->get('country'));
                    $r->setUser($user);
                    $r->setActive(true);

                    $dt = date_create_from_format('d.m.Y. H:i', $request->get('birthDate'));
                    $r->setBirthDate($dt);

                    $this->getDoctrine()->getManager()->persist($r);
                    $this->getDoctrine()->getManager()->flush();

                    $this->get('session')->getFlashBag()->add('success', 'Profile created');
                    return $this->redirectToRoute('site.refugees');
                    break;

                case 'person':
                    $c = new Citizen();
                    $c->setActive(true);
                    $c->setFirstName($request->get('firstName'));
                    $c->setLastName($request->get('lastName'));
                    $c->setCountry($request->get('country'));
                    $c->setUser($user);

                    $this->getDoctrine()->getManager()->persist($c);
                    $this->getDoctrine()->getManager()->flush();

                    $this->get('session')->getFlashBag()->add('success', 'Profile created');
                    return $this->redirectToRoute('site.citizens');
                    break;
            }

            $this->getDoctrine()->getManager()->flush();
            var_dump($type);
            exit;

        }

        return compact('type');
    }

    /**
     * @Route("/refugees", name="site.refugees")
     * @Template()
     */
    public function refugeesAction(Request $request)
    {
        /** @var RoleService $roleService */
        $roleService = $this->get('role_service');
        $profiles = $roleService->getUserRole($this->getUser());

        if ($request->getMethod() == 'GET') {
            return [];
        } elseif ($request->getMethod() == 'POST') {

            $r = new \AppBundle\Entity\Request();
            $r->setActive(true);
            $r->setRefugee($profiles['profile_refugee']);
            $r->setTitle($request->get('title'));
            $r->setDescription($request->get('description'));
            $r->setLatitude($request->get('lat'));
            $r->setLongitude($request->get('lng'));
            $this->getDoctrine()->getManager()->persist($r);
            $this->getDoctrine()->getManager()->flush();
            $this->get('session')->getFlashBag()->add('success', 'Help requested!');
            return $this->redirectToRoute('homepage');
        }

        return [];
    }

    /**
     * @Route("/citizens", name="site.citizens")
     * @Template()
     */
    public function citizensAction(Request $request)
    {
        /** @var RoleService $roleService */
        $roleService = $this->get('role_service');
        $profiles = $roleService->getUserRole($this->getUser());

        if ($request->getMethod() == 'GET') {
            return [];
        } elseif ($request->getMethod() == 'POST') {

            $r = new \AppBundle\Entity\Offer();
            $r->setTitle($request->get('title'));
            $r->setActive(true);
            $r->setCitizen($profiles['profile_citizen']);
            $r->setDescription($request->get('description'));
            $r->setLatitude($request->get('lat'));
            $r->setLongitude($request->get('lng'));
            $this->getDoctrine()->getManager()->persist($r);
            $this->getDoctrine()->getManager()->flush();
            $this->get('session')->getFlashBag()->add('success', 'Thank you!');
            return $this->redirectToRoute('homepage');
        }

        return [];
    }
}
