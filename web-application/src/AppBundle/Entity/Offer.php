<?php

namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use Gedmo\Timestampable\Traits\TimestampableEntity;
use Zantolov\AppBundle\Entity\Traits\ActivableTrait;
use Zantolov\AppBundle\Entity\Traits\BasicEntityTrait;
use Zantolov\MediaBundle\Entity\Traits\ImageableTrait;

/**
 * @ORM\Entity ()
 * @ORM\Table(name="offers")
 * @ORM\HasLifecycleCallbacks
 */
class Offer implements \JsonSerializable
{
    use BasicEntityTrait;
    use TimestampableEntity;
    use ActivableTrait;
    use ImageableTrait;


    /**
     * @var string
     *
     * @ORM\Column(name="title", type="string", length=255)
     */
    private $title;

    /**
     * @var string
     *
     * @ORM\Column(name="description", type="text")
     */
    private $description;

    /**
     * @var float
     * @ORM\Column(type="decimal", precision=18, scale=14)
     */
    private $latitude;

    /**
     * @var float
     * @ORM\Column(type="decimal", precision=18, scale=14)
     */
    private $longitude;


    /**
     * @ORM\ManyToOne(targetEntity="Citizen", inversedBy="offers")
     * @ORM\JoinColumn(name="citizen_id", referencedColumnName="id")
     */
    private $citizen;


    /**
     * @ORM\ManyToOne(targetEntity="Refugee", inversedBy="acceptedOffers")
     * @ORM\JoinColumn(name="refugee_id", referencedColumnName="id")
     */
    private $refugee;


    /**
     * @return string
     */
    public function getTitle()
    {
        return $this->title;
    }

    /**
     * @param string $title
     */
    public function setTitle($title)
    {
        $this->title = $title;
    }

    /**
     * @return string
     */
    public function getDescription()
    {
        return $this->description;
    }

    /**
     * @param string $description
     */
    public function setDescription($description)
    {
        $this->description = $description;
    }

    /**
     * @return float
     */
    public function getLatitude()
    {
        return $this->latitude;
    }

    /**
     * @param float $latitude
     */
    public function setLatitude($latitude)
    {
        $this->latitude = $latitude;
    }

    /**
     * @return float
     */
    public function getLongitude()
    {
        return $this->longitude;
    }

    /**
     * @param float $longitude
     */
    public function setLongitude($longitude)
    {
        $this->longitude = $longitude;
    }


    public function jsonSerialize()
    {
        return array(
            'id'          => $this->getId(),
            'title'       => $this->getTitle(),
            'description' => $this->getDescription(),
            'latitude'    => $this->getLatitude(),
            'longitude'   => $this->getLongitude(),
            'createdAt'   => $this->getCreatedAt()->format(DATE_ATOM),
        );
    }

    /**
     * @return mixed
     */
    public function getCitizen()
    {
        return $this->citizen;
    }

    /**
     * @param mixed $c
     */
    public function setCitizen($c)
    {
        $this->citizen = $c;
    }

    /**
     * @return mixed
     */
    public function getRefugee()
    {
        return $this->refugee;
    }

    /**
     * @param mixed $refugee
     */
    public function setRefugee($refugee)
    {
        $this->refugee = $refugee;
    }

}