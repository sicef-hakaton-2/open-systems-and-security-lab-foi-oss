<?php

namespace ApiBundle\Controller;


use AppBundle\Entity\Offer;
use AppBundle\Service\RoleService;
use Symfony\Component\HttpFoundation\JsonResponse;
use Zantolov\AppBundle\Controller\API\ApiController;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Zantolov\AppBundle\Entity\User;
use Zantolov\BlogBundle\Repository\PostRepository;


/**
 *
 * @Route("/api/news")
 */
class NewsApiController extends ApiController
{

    /**
     *
     * @Route("/", name="news.index")
     * @Method("GET")
     */
    public function indexAction()
    {
        /**
         * @var PostRepository $repo
         */
        $repo = $this->getDoctrine()->getManager()->getRepository('ZantolovBlogBundle:Post');
        $items = $repo->getActivePosts();

        $result = [];
        foreach ($items as $item) {
            $result[] = [
                'id'    => $item->getId(),
                'title' => $item->getTitle(),
                'text'  => $item->getIntro(),
                'image' => '/media/cache/postLeadingImage/uploads/images/default/' . $item->getImage()->getImageName()
            ];
        }

        return $this->createResponse([
            self::KEY_STATUS => self::STATUS_OK,
            self::KEY_DATA   => $result
        ]);
    }

    /**
     *
     * @Route("/{id}", name="news.details")
     * @Method("GET")
     */
    public function detailsAction($id)
    {
        /**
         * @var PostRepository $repo
         */
        $repo = $this->getDoctrine()->getManager()->getRepository('ZantolovBlogBundle:Post');
        $item = $repo->find($id);

        $result = [
            'id'    => $item->getId(),
            'title' => $item->getTitle(),
            'text'  => $item->getBody(),
            'image' => '/media/cache/postLeadingImage/uploads/images/default/' . $item->getImage()->getImageName()
        ];

        return $this->createResponse([
            self::KEY_STATUS => self::STATUS_OK,
            self::KEY_DATA   => $result
        ]);
    }
}