<?php

require_once __DIR__ . '/TestLoginTrait.php';

class ProfileEndpointTest extends \Codeception\TestCase\Test
{
    use TestLoginTrait;

    /**
     * @var \ApiTester
     */
    protected $tester;

    protected function _before()
    {
    }

    protected function _after()
    {
    }

    public function genericTest()
    {
        $this->tester->seeResponseJsonMatchesJsonPath('$.status');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.profile_refugee');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.profile_citizen');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.profile_authority');
    }

    public function checkProfile($name)
    {
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.profile_' . $name . '.firstName');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.profile_' . $name . '.lastName');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.profile_' . $name . '.country');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.profile_' . $name . '.createdAt');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.profile_' . $name . '.profileImage');

    }

    // tests
    public function testGetProfileRefugee()
    {
        $token = $this->doLogin('refugee1@mailinator.com', '123456');
        $this->tester->haveHttpHeader('Content-type', 'application/json');
        $this->tester->haveHttpHeader('X-Api-Token', $token);
        $this->tester->sendGET('/api/user/profile');
        $this->tester->seeResponseCodeIs(200);
        $this->tester->seeResponseIsJson();

        $this->genericTest();
        $this->checkProfile('refugee');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.profile_refugee.birthDate');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.profile_refugee.externalId');

    }

    // tests
    public function testGetProfileCitizen()
    {
        $token = $this->doLogin('citizen1@mailinator.com', '123456');
        $this->tester->haveHttpHeader('Content-type', 'application/json');
        $this->tester->haveHttpHeader('X-Api-Token', $token);
        $this->tester->sendGET('/api/user/profile');
        $this->tester->seeResponseCodeIs(200);
        $this->tester->seeResponseIsJson();

        $this->genericTest();
        $this->checkProfile('citizen');
    }

    public function testGetProfileAuthority()
    {
        $token = $this->doLogin('authority1@mailinator.com', '123456');
        $this->tester->haveHttpHeader('Content-type', 'application/json');
        $this->tester->haveHttpHeader('X-Api-Token', $token);
        $this->tester->sendGET('/api/user/profile');
        $this->tester->seeResponseCodeIs(200);
        $this->tester->seeResponseIsJson();

        $this->genericTest();
        $this->checkProfile('authority');
    }

    public function testGetOffers()
    {
        // Citizen
        $token = $this->doLogin('citizen1@mailinator.com', '123456');
        $this->tester->haveHttpHeader('Content-type', 'application/json');
        $this->tester->haveHttpHeader('X-Api-Token', $token);
        $this->tester->sendGET('/api/user/profile/offers');
        $this->tester->seeResponseCodeIs(200);
        $this->tester->seeResponseIsJson();

        // Refugee
        $token = $this->doLogin('refugee1@mailinator.com', '123456');
        $this->tester->haveHttpHeader('Content-type', 'application/json');
        $this->tester->haveHttpHeader('X-Api-Token', $token);
        $this->tester->sendGET('/api/user/profile/offers');
        $this->tester->seeResponseCodeIs(200);
        $this->tester->seeResponseIsJson();

    }
}